package com.arny.weatherly.presentation.states

sealed class Message(open val message: String) {
    companion object {
        // Tập trung quản lý message cố định ở đây, dễ thay đổi toàn bộ app
        const val NO_INTERNET = "No internet connection"
        const val LOCATION_PERMISSION_DENIED = "Location permission denied"
        const val LOCATION_DISABLED = "Location disabled"
        const val UNABLE_TO_RETRIEVE_WEATHER = "Unable to retrieve weather"
    }

    data object NoInternet : Message(NO_INTERNET)
    data object LocationPermissionDenied : Message(LOCATION_PERMISSION_DENIED)
    data object LocationDisabled : Message(LOCATION_DISABLED)
    data object UnableToRetrieveWeather : Message(UNABLE_TO_RETRIEVE_WEATHER)
    data class Custom(override val message: String) : Message(message)  // Cho dynamic message
}
