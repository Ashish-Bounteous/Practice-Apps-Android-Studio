//package com.app.imagetotextconverter.views
//
//import androidx.camera.view.CameraController
//import androidx.camera.view.LifecycleCameraController
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.app.imagetotextconverter.ui.theme.ImageToTextConverterTheme
//
//@Composable
//fun CameraScreen(navController: NavController) {
//    val lifeCycleOwner = LocalLifecycleOwner.current
//    val currentContext = LocalContext.current
//    val controller = remember {
//        LifecycleCameraController(currentContext).apply {
//            setEnabledUseCases(
//                CameraController.IMAGE_CAPTURE or
//                CameraController.IMAGE_ANALYSIS
//            )
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        AndroidView(factory = {
//            PreviewView(it).apply {
//                this.controller = controller
//                controller.bindToLifecycle(lifeCycleOwner)
//            }
//        }, modifier = Modifier.fillMaxSize())
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CameraScreenPreview() {
//    ImageToTextConverterTheme {
//        CameraScreen(rememberNavController())
//    }
//}