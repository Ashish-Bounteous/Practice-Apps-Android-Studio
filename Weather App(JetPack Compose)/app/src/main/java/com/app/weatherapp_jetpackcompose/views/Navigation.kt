package com.app.weatherapp_jetpackcompose.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.weatherapp_jetpackcompose.models.WeatherDataViewModel

@Composable
fun Navigation(viewModel: WeatherDataViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Weather_search_screen") {
        composable("Weather_search_screen") {
            WeatherSearchScreen(navController, viewModel)
        }
        composable("Weather_detail_screen") {
            WeatherDataScreen(navController, viewModel)
        }
    }
}