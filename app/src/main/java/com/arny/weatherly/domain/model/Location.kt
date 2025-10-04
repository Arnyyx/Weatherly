package com.arny.weatherly.domain.model

import com.arny.weatherly.data.local.entity.LocationEntity
import com.arny.weatherly.data.local.entity.WeatherEntity
import com.google.gson.Gson

data class Location(
    val ward: String = "",
    val city: String = "",
    val country: String = "",
    val fullAddress: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

fun Location.toEntity(): LocationEntity {
    return LocationEntity(
        ward = ward,
        city = city,
        country = country,
        fullAddress = fullAddress,
        latitude = latitude,
        longitude = longitude,
        ttl = 0
    )
}