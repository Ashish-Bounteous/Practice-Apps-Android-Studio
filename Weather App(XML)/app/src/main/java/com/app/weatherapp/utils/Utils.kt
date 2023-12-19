package com.app.weatherapp.utils

import android.content.Context
import android.widget.Toast

class Utils {
    companion object {
        fun displayToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }
}