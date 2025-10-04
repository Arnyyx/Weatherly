package com.arny.weatherly.data.remote.dto

import com.arny.weatherly.domain.model.Location
import com.google.gson.annotations.SerializedName
import java.util.Locale

data class LocationDto(
    val name: String,
    @SerializedName("local_names")
    val localNames: Map<String, String>? = null,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)

fun LocationDto.toDomain(): Location {
    return Location(
        ward = state ?: "",
        city = name,
        country = getCountryName(country),
        fullAddress = listOfNotNull(name, state, country).joinToString(", "),
        latitude = lat,
        longitude = lon,
    )
}

fun getCountryName(countryCode: String): String {
    return try {
        Locale.Builder().setRegion(countryCode).build().displayCountry
    } catch (_: Exception) {
        countryCode
    }
}