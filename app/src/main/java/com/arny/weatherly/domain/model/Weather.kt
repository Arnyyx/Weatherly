package com.arny.weatherly.domain.model

import com.arny.weatherly.data.local.entity.WeatherEntity
import com.google.gson.Gson

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezoneOffset: Int,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>?,
    val daily: List<DailyWeather>
)

data class CurrentWeather(
    val timestamp: Long,
    val sunrise: Long?,
    val sunset: Long?,
    val temperature: Double,
    val feelsLike: Float,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvIndex: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDirection: Int,
    val windGust: Double?,
    val conditions: List<WeatherCondition>,
    val rain: Rain?,
    val snow: Snow?
)

data class HourlyWeather(
    val timestamp: Long,
    val temperature: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvIndex: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDirection: Int,
    val windGust: Double?,
    val conditions: List<WeatherCondition>,
    val precipitationProbability: Double,
    val rain: Rain?,
    val snow: Snow?
)

data class DailyWeather(
    val timestamp: Long,
    val sunrise: Long?,
    val sunset: Long?,
    val moonrise: Long?,
    val moonset: Long?,
    val moonPhase: Double?,
    val summary: String?,
    val temperature: Temperature,
    val feelsLike: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val windGust: Double?,
    val conditions: List<WeatherCondition>,
    val clouds: Int,
    val precipitationProbability: Double,
    val rain: Double?,
    val snow: Double?,
    val uvIndex: Double
)

data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Rain(
    val oneHour: Double?
)

data class Snow(
    val oneHour: Double?
)

data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val evening: Double,
    val morning: Double
)

data class FeelsLike(
    val day: Double,
    val night: Double,
    val evening: Double,
    val morning: Double
)

fun Weather.toEntity(): WeatherEntity {
    val gson = Gson()
    return WeatherEntity(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        timezoneOffset = timezoneOffset,
        current = gson.toJson(current),
        hourly = gson.toJson(hourly),
        daily = gson.toJson(daily),
        ttl = 0
    )
}