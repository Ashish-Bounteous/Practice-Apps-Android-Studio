package com.app.weatherapp_jetpackcompose.utils

import com.app.weatherapp_jetpackcompose.BuildConfig

class Constants {
    companion object{
        const val baseUrl = "https://api.openweathermap.org/data/2.5/"
        const val apiKey = BuildConfig.API_KEY
    }
}