package com.app.imagetotextconverter.views

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.imagetotextconverter.ui.theme.*
import com.app.imagetotextconverter.utils.Utils
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConverterScreen(
    navController: NavController, selectedUri: MutableState<Uri?>,
    selectedUrl: MutableState<String?>
) {
    val convertedText = remember {
        mutableStateOf<String?>(null)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    coroutineScope.launch {
        Utils.extractTextFromImage(
            context,
            selectedUri,
            selectedUrl,
            isLoading,
            convertedText
        )
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .padding(top = 20.dp)
    ) {
        if (isLoading.value.not()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TransparentTopAppBar {
                    navController.popBackStack()
                    selectedUrl.value = null
                    selectedUri.value = null
                }
                if (!convertedText.value.isNullOrEmpty()) {
                    Button(
                        onClick = {
                            val text = convertedText.value
                            if (!text.isNullOrEmpty()) Utils.sendEmail(text, context)
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White, containerColor = cSkyBlue
                        )
                    ) {
                        Text(
                            text = "Send Email",
                            fontSize = 16.sp,
                            color = Color.White,
                            letterSpacing = 1.sp,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = if (isLoading.value or convertedText.value.isNullOrEmpty()) Arrangement.Center else Arrangement.Top,
            horizontalAlignment = if (isLoading.value or convertedText.value.isNullOrEmpty()) Alignment.CenterHorizontally else Alignment.Start
        ) {
            if (isLoading.value) Text(text = "Processing Image...")
            else {
                convertedText.value?.let {
                    Text(text = "Processed Text: $it")
                } ?: Text(text = "Unable to process Image")
            }
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTopAppBar(onBack: () -> Unit) {
    ElevatedButton(
        onClick = onBack,
        modifier = Modifier.background(color = Color.Transparent),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 20.dp),
        shape = RoundedCornerShape(100)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"

        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ConverterScreenPreview() {
    val selectedUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val selectedUrl = remember {
        mutableStateOf<String?>(null)
    }
    ImageToTextConverterTheme {
        ConverterScreen(rememberNavController(), selectedUri, selectedUrl)
    }
}