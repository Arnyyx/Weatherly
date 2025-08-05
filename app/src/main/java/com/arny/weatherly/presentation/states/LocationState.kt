package com.arny.weatherly.presentation.states

import com.arny.weatherly.domain.model.Location

sealed interface LocationState {
    object Loading : LocationState

    data class Success(
        val location: Location
    ) : LocationState

    data class Error(val message: String) : LocationState
}