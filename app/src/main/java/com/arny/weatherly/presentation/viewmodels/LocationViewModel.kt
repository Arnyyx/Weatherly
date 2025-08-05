package com.arny.weatherly.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arny.weatherly.domain.repository.LocationRepository
import com.arny.weatherly.presentation.states.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private var _locationState = MutableStateFlow<LocationState>(LocationState.Loading)
    val locationState: StateFlow<LocationState> = _locationState.asStateFlow()

    init {
        fetchLocation()
    }

    fun fetchLocation() {
        _locationState.value = LocationState.Loading
        viewModelScope.launch {
            locationRepository.getLocation().onSuccess { location ->
                _locationState.value = LocationState.Success(location)
            }.onFailure {
                _locationState.value =
                    LocationState.Error(it.message ?: "Unable to retrieve location")
            }
        }
    }

    fun onPermissionDenied() {
        _locationState.value = LocationState.Error("Location permission denied")
    }

    fun onLocationServicesDisabled() {
        _locationState.value = LocationState.Error("Please enable location services")
    }
}