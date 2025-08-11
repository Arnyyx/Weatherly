package com.arny.weatherly.presentation.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arny.weatherly.presentation.states.LocationState
import com.arny.weatherly.presentation.states.Response
import com.arny.weatherly.presentation.states.Warning
import com.arny.weatherly.presentation.states.WeatherState
import com.arny.weatherly.presentation.viewmodels.LocationViewModel
import com.arny.weatherly.presentation.viewmodels.WeatherViewModel

@Composable
fun TestScreen(
    locationViewModel: LocationViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val locationState by locationViewModel.locationState.collectAsState()
    val weatherState by weatherViewModel.weatherState.collectAsState()
    val context = LocalContext.current

    // Fetch weather when location is successfully retrieved
    LaunchedEffect(locationState) {
        Log.d("TestScreen", "locationState: $locationState")
        when (val response = locationState.locationState) {
            is Response.Success -> {
                response.data.let { location ->
                    weatherViewModel.getWeather(location.latitude, location.longitude)
                }
            }

            else -> Unit
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display warnings if any
            locationState.locationState.let { response ->
                if (response is Response.Success) {
                    response.warning?.let { warning ->
                        WarningMessage(warning)
                    }
                }
            }

            Button(onClick = { locationViewModel.fetchLocation(context) }) {
                Text("Fetch Location")
            }

            // Display location information
            LocationInfo(locationState)

            Spacer(modifier = Modifier.height(16.dp))

            // Display weather information
            WeatherInfo(weatherState)
        }
    }
}

@Composable
fun WarningMessage(warning: Warning) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                when (warning) {
                    Warning.NoInternet -> {
                        context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                    }

                    Warning.LocationPermissionDenied -> {
                        context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = android.net.Uri.fromParts("package", context.packageName, null)
                        })
                    }

                    Warning.LocationDisabled -> {
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Text(
            text = when (warning) {
                Warning.NoInternet -> "No internet connection"
                Warning.LocationPermissionDenied -> "Location permission denied"
                Warning.LocationDisabled -> "Location services disabled"
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LocationInfo(locationState: LocationState) {
    when (val response = locationState.locationState) {
        is Response.Loading -> {
            CircularProgressIndicator()
            Text(
                text = "Fetching location...",
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        is Response.Success -> {
            response.data.let { location ->
                Text(
                    text = "Location: ${location.city}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Latitude: ${location.latitude}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Longitude: ${location.longitude}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        is Response.Error -> {
            Text(
                text = "Error: ${response.errorMessage}",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Response.Idle -> {}
    }
}

@Composable
fun WeatherInfo(weatherState: WeatherState) {
    when (val response = weatherState.weatherState) {
        is Response.Loading -> {
            CircularProgressIndicator()
            Text(
                text = "Fetching weather...",
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        is Response.Success -> {
            response.data.let { weather ->
                Text(
                    text = "Weather: ${weather.current?.rain}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Temperature: ${weather.current?.temp}°C",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Humidity: ${weather.current?.humidity}%",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        is Response.Error -> {
            Text(
                text = "Error: ${response.errorMessage}",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is Response.Idle -> {}
    }
}