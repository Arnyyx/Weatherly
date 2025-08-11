package com.arny.weatherly.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun WeatherTheme(
    weatherType: WeatherTheme,
    content: @Composable () -> Unit
) {
    val colorScheme = when (weatherType) {
        WeatherTheme.Clear -> RainyColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
