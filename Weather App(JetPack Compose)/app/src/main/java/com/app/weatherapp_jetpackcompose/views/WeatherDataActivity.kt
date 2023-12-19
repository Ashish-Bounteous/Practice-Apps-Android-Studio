package com.app.weatherapp_jetpackcompose.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.weatherapp_jetpackcompose.entities.WeatherEntity
import com.app.weatherapp_jetpackcompose.models.WeatherDataViewModel
import com.app.weatherapp_jetpackcompose.ui.theme.CustomDarkBlue
import com.app.weatherapp_jetpackcompose.ui.theme.WeatherAppJetPackComposeTheme
import com.app.weatherapp_jetpackcompose.ui.theme.customGlassGrey
import com.app.weatherapp_jetpackcompose.utils.Utils
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun WeatherDataScreen(navController: NavController, viewModel: WeatherDataViewModel) {
    val data = viewModel.weatherResponse
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }
    if(!viewModel.isSuccess.value && !viewModel.loading.value){
        navigateBack()
        Utils.displayToast(LocalContext.current, "Something went wrong, please try again...")
    }
    data?.let {
        val location = LatLng(data.coord?.lat!!, data.coord.lon!!)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 10f)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = Utils.setWeatherActivityBackground(data.weather[0].main!!)),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            TransparentTopAppBar(navigateBack)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedWeatherInfo(data)
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(24.dp)), cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = location),
                        title = data.name!!,
                    )
                }
                WeatherDetailsRow(data)
            }
        }
    } ?: Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(text = "Loading...")
    }
}

@Composable
private fun RoundedWeatherInfo(data: WeatherEntity) {
    Column(
        modifier = Modifier
            .background(color = customGlassGrey, shape = RoundedCornerShape(100))
            .padding(50.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data.name!!,
            fontSize = 30.sp,
            color = Color.White,
            letterSpacing = 1.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${data.main!!.temp!!.toInt()}℃",
            fontSize = 100.sp, color = Color.White,
        )
        Text(
            text = data.weather[0].main!!,
            fontSize = 24.sp, color = Color.White,
        )
    }
}

@Composable
private fun WeatherDetailsRow(data: WeatherEntity) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(color = customGlassGrey, shape = RoundedCornerShape(5.dp))
            .padding(vertical = 15.dp, horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1F)
        ) {
            WeatherDetail("Lat:", data.coord?.lat.toString())
            WeatherDetail("Humid:", data.main!!.humidity!!.toString())
            WeatherDetail("Wind:", "${data.wind!!.speed}mph")
        }
        Column(
            modifier = Modifier.weight(1F)
        ) {
            WeatherDetail("Lon:", data.coord?.lon.toString())
            WeatherDetail("Pressure:", data.main?.pressure.toString())
            WeatherDetail("Country:", data.sys!!.country!!)
        }
    }
}

@Composable
private fun WeatherDetail(label: String, value: String) {

    Row {
        Text(
            text = "$label ", color = Color.White, fontSize = 16.sp
        )
        Text(
            modifier = Modifier.weight(1F),
            text = value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.size(10.dp))

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTopAppBar(onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("") },
        navigationIcon = {
            ElevatedButton(
                onClick = onBack,
                modifier = Modifier.background(color = Color.Transparent),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 20.dp),
                shape = RoundedCornerShape(100)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"

                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun WeatherDataScreenPreview() {
    WeatherAppJetPackComposeTheme {
        WeatherDataScreen(rememberNavController(), WeatherDataViewModel())
    }
}
