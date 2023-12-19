package com.app.weatherapp_jetpackcompose.utils

import android.content.Context
import android.widget.Toast
import com.app.weatherapp_jetpackcompose.R

class Utils {
    companion object {
        fun displayToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun setWeatherActivityBackground(weatherType: String): Int {
            return when (weatherType) {
                "Thunderstorm" -> R.drawable.thunderstorm_mobile
                "Drizzle", "Rain" -> R.drawable.rainy_mobile
                "Snow" -> R.drawable.snow_mobile
                "Clear" -> R.drawable.sunny_mobile
                else -> R.drawable.cloudy_mobile
            }
        }

    }
}