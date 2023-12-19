package com.app.weatherapp_jetpackcompose.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.weatherapp_jetpackcompose.models.WeatherDataViewModel
import com.app.weatherapp_jetpackcompose.ui.theme.CustomDarkBlue
import com.app.weatherapp_jetpackcompose.ui.theme.CustomGrey
import com.app.weatherapp_jetpackcompose.ui.theme.CustomSkyBlue
import com.app.weatherapp_jetpackcompose.ui.theme.WeatherAppJetPackComposeTheme

@Composable
fun WeatherSearchScreen(navController: NavController, viewModel: WeatherDataViewModel) {
    val searchText = remember {
        mutableStateOf("")
    }
    val buttonBackground = remember {
        mutableStateOf(CustomGrey)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomDarkBlue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Weather", fontSize = 32.sp, color = Color.White)

        Spacer(modifier = Modifier.size(25.dp))

        TextField(shape = RoundedCornerShape(5.dp), value = searchText.value, onValueChange = {
            buttonBackground.value = if (it.length > 1) CustomSkyBlue else CustomGrey
            searchText.value = it
        }, trailingIcon = {
            IconButton(onClick = {/* onclick */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
            }
        }, placeholder = { Text("Search for Place") })

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                viewModel.fetchWeatherData(searchText.value)
                navController.navigate("Weather_detail_screen")
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .padding(horizontal = 35.dp, vertical = 10.dp)
                .clickable { searchText.value.length > 1 },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = buttonBackground.value
            )
        ) {
            Text(
                text = "Search",
                fontSize = 16.sp,
                color = Color.White,
                letterSpacing = 1.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherSearchScreenPreview() {
    WeatherAppJetPackComposeTheme {
        WeatherSearchScreen(rememberNavController(), WeatherDataViewModel())
    }
}

