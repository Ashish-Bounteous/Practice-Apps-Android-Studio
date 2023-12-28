package com.app.imagetotextconverter.views

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val selectedUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val selectedUrl = remember {
        mutableStateOf<String?>(null)
    }
    NavHost(navController = navController, startDestination = "Home_Screen") {
        composable("Home_Screen"){
            HomeScreen(navController, selectedUri, selectedUrl)
        }
        composable("ImageToTextConverter_Screen"){
            ConverterScreen(navController, selectedUri, selectedUrl)
        }
    }
}