package com.app.weatherapp_jetpackcompose.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.app.weatherapp_jetpackcompose.models.WeatherDataViewModel
import com.app.weatherapp_jetpackcompose.ui.theme.WeatherAppJetPackComposeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppJetPackComposeTheme {
                Navigation(viewModel)
            }
        }
    }
}