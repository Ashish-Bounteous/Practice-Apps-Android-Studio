package com.app.weatherapp_jetpackcompose.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weatherapp_jetpackcompose.entities.WeatherEntity
import com.app.weatherapp_jetpackcompose.repositories.WeatherRepository
import kotlinx.coroutines.launch

class WeatherDataViewModel : ViewModel(){
        private val repository = WeatherRepository()

    private val _weatherResponse = mutableStateOf<WeatherEntity?>(null)
    val weatherResponse get() = _weatherResponse.value
    var isSuccess = mutableStateOf<Boolean>(false)
    var loading = mutableStateOf<Boolean>(false)

        fun fetchWeatherData(searchQuery: String) {
            viewModelScope.launch {
                try {
                    loading.value = true
                    val data = repository.getWeatherData(searchQuery)

                    if (data.isSuccessful){
                        _weatherResponse.value = data.body()
                        isSuccess.value=true
                    }
                    else _weatherResponse.value = null

                    loading.value = false
                } catch (_: Exception) {
                    loading.value = false
                }
            }
        }

}