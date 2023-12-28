package com.app.imagetotextconverter.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.core.content.FileProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


object Utils {
    fun displayToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun sendEmail(content: String, context: Context) {
        try {
            val fileName = "CONTENT.txt"
            val file = createTextFile(context, fileName, content)

            val fileUri =
                FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, fileUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent.createChooser(
                    intent,
                    "Choose an Email client : "
                )
            )
        } catch (e: IOException) {
            displayToast(context, "Unable to send Email, please try again...")
        }
    }

    @Throws(IOException::class)
    private fun createTextFile(context: Context, fileName: String, content: String): File {
        val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(externalFilesDir, fileName)

        FileOutputStream(file).use {
            it.write(content.toByteArray())
        }

        return file
    }

    suspend fun extractTextFromImage(
        context: Context,
        uri: MutableState<Uri?>,
        url: MutableState<String?>,
        isLoading: MutableState<Boolean>,
        text: MutableState<String?>
    ) {
        // 1. Create an instance of TextRecognizer
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        // 2. Prepare the input image
        val uriString = uri.value
        val urlString = url.value

        val image: InputImage?

        if (uriString == null && urlString == null) {
            isLoading.value = false
            return
        }
        val bitMapImage: Bitmap? = loadImageFromUrl(urlString)
        image = if (bitMapImage == null) InputImage.fromFilePath(
            context,
            uriString!!
        ) else InputImage.fromBitmap(bitMapImage, 0)

        // 3. Process the image
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                var lineText: String = ""
                for (block in visionText.textBlocks) {
                    for (line in block.lines) {
                        lineText += line.text + "\n"
                    }
                }
                text.value = lineText.trimEnd()
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
            }.addOnCompleteListener { isLoading.value = false }


    }

    private suspend fun loadImageFromUrl(url: String?): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val urlConnection = URL(url).openConnection()
                val inputStream = urlConnection.getInputStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

}

