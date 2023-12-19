package com.app.weatherapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.weatherapp.R
import com.app.weatherapp.dao.WeatherData
import com.app.weatherapp.databinding.WeatherDataActivityBinding


class WeatherDataActivity : AppCompatActivity() {

    private lateinit var binding: WeatherDataActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeatherDataActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weatherData = intent.getSerializableExtra("data") as WeatherData

        setWeatherActivityBackground(weatherData.weather[0].main.toString())
        displayWeatherData(weatherData)

        binding.goBackButton.setOnClickListener {
            finish()
        }
    }
    private fun displayWeatherData(weatherData: WeatherData){
        with(binding){
            weatherPlaceText.text = weatherData.name
            weatherDegreeText.text = getString(R.string.degree, weatherData.main?.temp?.toInt().toString())
            weatherTypeText.text = weatherData.weather[0].main.toString()
            latInputText.text = weatherData.coord?.lat.toString()
            lonInputText.text = weatherData.coord?.lon.toString()
            humidInputText.text = weatherData.main?.humidity.toString()
            pressureInputText.text = weatherData.main?.pressure.toString()
            windInputText.text = weatherData.wind?.speed.toString()
            countryInputText.text = weatherData.sys?.country
        }
    }

    private fun setWeatherActivityBackground(weatherType: String){
        val backgroundType = when (weatherType) {
            "Thunderstorm" -> R.drawable.thunderstorm_mobile
            "Drizzle", "Rain" -> R.drawable.rainy_mobile
            "Snow" -> R.drawable.snow_mobile
            "Clear" -> R.drawable.sunny_mobile
            else -> R.drawable.cloudy_mobile
        }
        binding.weatherSceenLayout.setBackgroundResource(backgroundType)
    }
}