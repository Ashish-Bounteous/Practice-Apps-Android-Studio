package com.app.weatherapp.activities

import android.content.Context
import android.content.Intent
import com.app.weatherapp.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.app.weatherapp.api.ApiService
import com.app.weatherapp.api.RetroFitInstance
import com.app.weatherapp.dao.WeatherData
import com.app.weatherapp.databinding.WeatherSearchActivityBinding
import com.app.weatherapp.utils.Constants
import com.app.weatherapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherSearchActivity : AppCompatActivity() {
    private lateinit var binding: WeatherSearchActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeatherSearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchTextInput.addTextChangeListener { text ->
            if (text.length > 1) {
                binding.searchButton.isEnabled = true
                binding.searchButton.setBackgroundResource(R.drawable.rectangle_button)
            } else {
                binding.searchButton.isEnabled = false
                binding.searchButton.setBackgroundResource(R.drawable.disabled_rectangle_button)
            }
        }

        binding.searchButton.setOnClickListener { performWeatherSearch() }

    }

    private fun performWeatherSearch() {
        hideKeyboard()
        binding.searchTextInput.clearFocus()
        binding.searchButton.text = "Loading..."

        CoroutineScope(Dispatchers.IO).launch {

            val weatherDataResponse =
                RetroFitInstance.api.getWeather(binding.searchTextInput.text.toString())

            withContext(Dispatchers.Main) {
                handleWeatherResponse(weatherDataResponse)
            }
        }
    }
    private fun handleWeatherResponse(weatherDataResponse: Response<WeatherData>) {
        if (weatherDataResponse.isSuccessful) {
            val intent = Intent(applicationContext, WeatherDataActivity::class.java)
            intent.putExtra("data", weatherDataResponse.body())
            startActivity(intent)
        } else {
            Utils.displayToast(
                this@WeatherSearchActivity,
                "Something went wront, please try again!!!"
            )
        }
        binding.searchTextInput.text.clear();
        binding.searchButton.text = "Search"
    }
    private fun EditText.addTextChangeListener(onTextChanged: (text: String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged.invoke(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
    private fun hideKeyboard() {
        currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}