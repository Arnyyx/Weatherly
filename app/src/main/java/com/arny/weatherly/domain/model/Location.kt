package com.arny.weatherly.domain.model

data class Location(
    val ward: String = "",
    val city: String = "",
    val country: String = "",
    val fullAddress: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
