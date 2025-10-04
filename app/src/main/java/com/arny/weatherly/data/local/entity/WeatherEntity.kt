package com.arny.weatherly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.arny.weatherly.data.local.converter.WeatherTypeConverters
import com.arny.weatherly.domain.model.CurrentWeather
import com.arny.weatherly.domain.model.DailyWeather
import com.arny.weatherly.domain.model.HourlyWeather
import com.arny.weatherly.domain.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "weather")
@TypeConverters(WeatherTypeConverters::class)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezoneOffset: Int,
    val current: String, // JSON string for CurrentWeather
    val hourly: String?, // JSON string for List<HourlyWeather>
    val daily: String, // JSON string for List<DailyWeather>
    val ttl: Long
)

fun WeatherEntity.toDomain(): Weather {
    val gson = Gson()
    return Weather(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        timezoneOffset = timezoneOffset,
        current = gson.fromJson(current, CurrentWeather::class.java),
        hourly = hourly?.let {
            gson.fromJson(
                it,
                object : TypeToken<List<HourlyWeather>>() {}.type
            )
        },
        daily = gson.fromJson(daily, object : TypeToken<List<DailyWeather>>() {}.type)
    )
}