package com.arny.weatherly.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arny.weatherly.data.local.LocationPreferences
import com.arny.weatherly.domain.repository.LocationRepository
import com.arny.weatherly.presentation.states.LocationState
import com.arny.weatherly.presentation.states.Response
import com.arny.weatherly.presentation.states.Warning
import com.arny.weatherly.utils.hasLocationPermission
import com.arny.weatherly.utils.isInternetAvailable
import com.arny.weatherly.utils.isLocationEnabled
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val locationPrefs: LocationPreferences,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState())
    val locationState: StateFlow<LocationState> = _locationState.asStateFlow()

    init {
        fetchLocation(context)
    }

    fun fetchLocation(context: Context) {
        viewModelScope.launch {
            var warning: Warning? = null
            if (!isInternetAvailable(context)) {
                warning = Warning.NoInternet
            }
            if (!hasLocationPermission(context)) {
                warning = Warning.LocationPermissionDenied
            }
            if (!isLocationEnabled(context)) {
                warning = Warning.LocationDisabled
            }
            _locationState.value = _locationState.value.copy(
                locationState = Response.Loading
            )
            locationRepository.getLocation().fold(
                onSuccess = { location ->
                    _locationState.value = _locationState.value.copy(
                        locationState = Response.Success(location, warning),
                    )
                    locationPrefs.saveLocation(location)
                },
                onFailure = { error ->
                    locationPrefs.locationFlow.first()?.let { cachedLocation ->
                        _locationState.value = _locationState.value.copy(
                            locationState = Response.Success(cachedLocation, warning)
                        )
                    } ?: run {
                        _locationState.value = _locationState.value.copy(
                            locationState = Response.Error(
                                error.message ?: "Unable to retrieve location"
                            )
                        )
                    }
                }
            )
        }
    }
}