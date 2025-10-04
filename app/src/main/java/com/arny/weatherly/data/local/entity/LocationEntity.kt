package com.arny.weatherly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arny.weatherly.domain.model.CurrentWeather
import com.arny.weatherly.domain.model.DailyWeather
import com.arny.weatherly.domain.model.HourlyWeather
import com.arny.weatherly.domain.model.Location
import com.arny.weatherly.domain.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val ward: String = "",
    val city: String = "",
    val country: String = "",
    val fullAddress: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val ttl: Long = 0L
)

fun LocationEntity.toDomain(): Location {
    return Location(
        ward = ward,
        city = city,
        country = country,
        fullAddress = fullAddress,
        latitude = latitude,
        longitude = longitude
    )
}