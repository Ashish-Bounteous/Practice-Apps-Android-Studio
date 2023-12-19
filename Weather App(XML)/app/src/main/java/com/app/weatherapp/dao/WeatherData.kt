package com.app.weatherapp.dao

import java.io.Serializable

data class WeatherData(
    val coord: Coordinates? = Coordinates(),
    val weather: ArrayList<Weather> = arrayListOf(),
    val main: Main? = Main(),
    val wind: Wind? = Wind(),
    val sys: Sys? = Sys(),
    val name: String? = null
) : Serializable

data class Coordinates(
    val lon: Double? = null,
    val lat: Double? = null
) : Serializable

data class Weather(
    val main: String? = null,
) : Serializable

data class Main(
    val temp: Double? = null,
    val pressure: Int? = null,
    val humidity: Int? = null
) : Serializable


data class Wind(
    val speed: Double? = null,
) : Serializable

data class Sys(
    val country: String? = null
) : Serializable