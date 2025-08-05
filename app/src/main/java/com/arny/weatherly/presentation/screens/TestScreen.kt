package com.arny.weatherly.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.arny.weatherly.domain.model.CurrentWeather
import com.arny.weatherly.domain.model.DailyWeather
import com.arny.weatherly.domain.model.HourlyWeather
import com.arny.weatherly.domain.model.WeatherResponse
import com.arny.weatherly.presentation.states.WeatherState
import com.arny.weatherly.presentation.viewmodels.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TestScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getWeather(21.015241982152446, 105.8257341637274)
    }
    val weatherState by viewModel.weatherState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (weatherState) {
            is WeatherState.Loading -> {
                Log.d("TestScreen", "Loading")
                CircularProgressIndicator()
            }

            is WeatherState.Success -> {
                Log.d("TestScreen", "Success")
                val weatherData = (weatherState as WeatherState.Success).weather
                WeatherContent(weatherData)
            }

            is WeatherState.Error -> {
                Log.d("TestScreen", "Error")
                Text(
                    text = (weatherState as WeatherState.Error).message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun WeatherContent(data: WeatherResponse) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Weather in ${data.timezone}",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        data.current?.let { current ->
            item {
                CurrentWeatherCard(current)
            }
        }
        data.hourly?.let { hourly ->
            item {
                Text(
                    text = "Hourly Forecast",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            items(hourly.take(24)) { hour ->
                HourlyWeatherRow(hour)
            }
        }
        data.daily?.let { daily ->
            item {
                Text(
                    text = "Daily Forecast",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            items(daily.take(7)) { day ->
                DailyWeatherRow(day)
            }
        }
    }
}

@Composable
fun CurrentWeatherCard(current: CurrentWeather) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            current.weather.firstOrNull()?.let { weather ->
                Image(
                    painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${weather.icon}@2x.png"),
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = weather.description.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "Temperature: ${"%.1f".format(current.temp)}°C",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Feels like: ${"%.1f".format(current.feels_like)}°C",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Humidity: ${current.humidity}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun HourlyWeatherRow(hour: HourlyWeather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(hour.dt * 1000)),
            style = MaterialTheme.typography.bodyMedium
        )
        hour.weather.firstOrNull()?.let { weather ->
            Image(
                painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${weather.icon}@2x.png"),
                contentDescription = "Weather icon",
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = "${"%.1f".format(hour.temp)}°C",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "PoP: ${"%.0f".format(hour.pop * 100)}%",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DailyWeatherRow(day: DailyWeather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = SimpleDateFormat("EEE, dd MMM", Locale.getDefault()).format(Date(day.dt * 1000)),
            style = MaterialTheme.typography.bodyMedium
        )
        day.weather.firstOrNull()?.let { weather ->
            Image(
                painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${weather.icon}@2x.png"),
                contentDescription = "Weather icon",
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = "${"%.1f".format(day.temp.max)}/${"%.1f".format(day.temp.min)}°C",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "PoP: ${"%.0f".format(day.pop * 100)}%",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}