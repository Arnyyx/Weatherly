package com.arny.weatherly.presentation.states

import com.arny.weatherly.domain.model.WeatherResponse

sealed interface WeatherState {
    object Loading : WeatherState

    data class Success(
        val weather: WeatherResponse
    ) : WeatherState

    data class Error(val message: String) : WeatherState
}