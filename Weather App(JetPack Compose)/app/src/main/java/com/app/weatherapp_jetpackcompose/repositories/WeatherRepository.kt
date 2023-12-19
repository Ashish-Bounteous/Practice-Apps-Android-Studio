package com.app.weatherapp_jetpackcompose.repositories

import com.app.weatherapp.api.RetroFitInstance
import com.app.weatherapp_jetpackcompose.entities.WeatherEntity
import retrofit2.Response

class WeatherRepository {
    private val weatherDataService = RetroFitInstance.api

    suspend fun getWeatherData(searchQuery: String): Response<WeatherEntity> {
        return weatherDataService.getWeather(searchQuery)
    }
}