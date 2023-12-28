package com.app.imagetotextconverter.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.imagetotextconverter.R
import com.app.imagetotextconverter.ui.theme.ImageToTextConverterTheme
import com.app.imagetotextconverter.ui.theme.cGray
import com.app.imagetotextconverter.ui.theme.cPurple
import com.app.imagetotextconverter.ui.theme.cSkyBlue

@Composable
fun HomeScreen(
    navController: NavController,
    selectedUri: MutableState<Uri?>,
    selectedUrl: MutableState<String?>
) {
    val openDialog = remember {
        mutableStateOf(false)
    }

    val photoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                selectedUri.value = uri
                navController.navigate("ImageToTextConverter_Screen")
            })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = cGray),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomIconButton(
                Modifier
                    .weight(1F)
                    .fillMaxHeight(), R.drawable.photo_camera, "Camera"
            )

            Spacer(modifier = Modifier.size(10.dp))

            Column(
                modifier = Modifier.weight(1F)
            ) {
                CustomIconButton(
                    Modifier
                        .weight(1F)
                        .fillMaxWidth(), R.drawable.gallery, "Gallery"
                ) {
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                Spacer(modifier = Modifier.size(10.dp))
                CustomIconButton(
                    Modifier
                        .weight(1F)
                        .fillMaxWidth(), R.drawable.link, "Image Url"
                ) { openDialog.value = !openDialog.value }
                if (openDialog.value) CustomDialog(openDialog, selectedUrl){navController.navigate("ImageToTextConverter_Screen")}
            }
        }
    }
}

@Composable
fun CustomIconButton(modifier: Modifier, iconId: Int, label: String, action: () -> Unit = {}) {
    IconButton(
        onClick = { action() },
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "link logo",
                tint = cPurple,
                modifier = Modifier.size(50.dp)
            )
            Text(text = label)
        }
    }
}

@Composable
fun CustomDialog(
    openDialog: MutableState<Boolean>,
    text: MutableState<String?>,
    action: ()->Unit
) {
    println(text.value)
    AlertDialog(onDismissRequest = { openDialog.value = false },
        confirmButton = {
            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        openDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White, containerColor = Color.Red
                    )
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {
                        openDialog.value = false
                        action()
                    },
                ) {
                    Text(text = "Confirm")
                }

            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = text.value ?: "",
                    onValueChange = { text.value = it.trim() },
                    label = {
                        Text(text = "Image URL")
                    }
                )
            }

        }
    )

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val selectedUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val selectedUrl = remember {
        mutableStateOf<String?>(null)
    }
    ImageToTextConverterTheme {
        HomeScreen(rememberNavController(), selectedUri, selectedUrl)
    }
}