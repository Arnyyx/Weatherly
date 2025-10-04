package com.arny.weatherly.presentation.states

import com.arny.weatherly.domain.model.Weather

data class WeatherState(
    val weatherState: Response<Weather> = Response.Idle
)