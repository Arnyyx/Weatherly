package com.arny.weatherly.presentation.states

sealed class Response<out T> {
    data object Idle : Response<Nothing>()
    data object Loading : Response<Nothing>()
    data class Success<out T>(
        val data: T,
        val warning: Warning? = null
    ) : Response<T>()

    data class Error(val errorMessage: String) : Response<Nothing>()
}