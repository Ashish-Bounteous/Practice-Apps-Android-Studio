package com.app.weatherapp_jetpackcompose.api

import com.app.weatherapp_jetpackcompose.entities.WeatherEntity
import com.app.weatherapp_jetpackcompose.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") searchQuery: String,
        @Query("appid") apiKey: String = Constants.apiKey,
        @Query("units") unit: String = "metric"
    ): Response<WeatherEntity>
}