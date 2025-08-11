package com.arny.weatherly.presentation.states

sealed class Warning(val message: String) {
    data object NoInternet : Warning("No internet connection")
    data object LocationPermissionDenied : Warning("Location permission denied")
    data object LocationDisabled : Warning("Vị trí hiện đang bị tắt.")
}
