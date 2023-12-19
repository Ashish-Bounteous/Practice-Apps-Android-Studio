package com.app.weatherapp.api

import com.app.weatherapp.dao.WeatherData
import com.app.weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") searchQuery: String,
        @Query("appid") apiKey: String = Constants.apiKey,
        @Query("units") unit: String = "metric"
    ): Response<WeatherData>
}