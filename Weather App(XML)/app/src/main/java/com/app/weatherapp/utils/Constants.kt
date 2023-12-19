package com.app.weatherapp.utils

import com.app.weatherapp.BuildConfig

class Constants {
    companion object{
        const val baseUrl = "https://api.openweathermap.org/data/2.5/"
        const val apiKey = BuildConfig.API_KEY
    }
}