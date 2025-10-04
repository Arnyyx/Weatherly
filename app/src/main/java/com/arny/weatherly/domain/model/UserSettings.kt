package com.arny.weatherly.domain.model

data class UserSettings(
    val weatherUnit: WeatherUnit = WeatherUnit.METRIC,
)

enum class WeatherUnit(val value: String) {
    STANDARD("standard"),
    METRIC("metric"),
    IMPERIAL("imperial")
}
