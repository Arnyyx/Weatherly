package com.arny.weatherly.presentation.states

import com.arny.weatherly.domain.model.Location

data class LocationState(
    val locationState: Response<Location> = Response.Idle,
)