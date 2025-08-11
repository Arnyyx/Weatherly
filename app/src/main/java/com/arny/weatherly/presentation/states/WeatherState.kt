package com.arny.weatherly.presentation.states

import com.arny.weatherly.domain.model.WeatherResponse

data class WeatherState(
    val weatherState: Response<WeatherResponse> = Response.Idle
)