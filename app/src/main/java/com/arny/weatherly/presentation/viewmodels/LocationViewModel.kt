package com.arny.weatherly.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arny.weatherly.domain.model.Location
import com.arny.weatherly.domain.repository.LocationRepository
import com.arny.weatherly.presentation.states.LocationState
import com.arny.weatherly.presentation.states.Message
import com.arny.weatherly.presentation.states.Response
import com.arny.weatherly.utils.hasLocationPermission
import com.arny.weatherly.utils.isInternetAvailable
import com.arny.weatherly.utils.isLocationEnabled
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState())
    val locationState: StateFlow<LocationState> = _locationState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _citiesState = MutableStateFlow<List<Location>>(emptyList())
    val citiesState: StateFlow<List<Location>> = _citiesState

    init {
        fetchLocation(context)
    }

    fun fetchLocation(context: Context) {
        viewModelScope.launch {
            var message: Message? = null
            if (!isInternetAvailable(context)) {
                message = Message.NoInternet
            }
            if (!hasLocationPermission(context)) {
                message = Message.LocationPermissionDenied
            }
            if (!isLocationEnabled(context)) {
                message = Message.LocationDisabled
            }
            _locationState.value = _locationState.value.copy(
                locationState = Response.Loading
            )
            locationRepository.getLocation().catch {
                _locationState.value =
                    _locationState.value.copy(
                        locationState = Response.Error(
                            message ?: Message.UnableToRetrieveWeather
                        )
                    )
            }.collect { result ->
                result.fold(
                    onSuccess = { location ->
                        _locationState.value = _locationState.value.copy(
                            locationState = Response.Success(location, message),
                        )
                    },
                    onFailure = { error ->
                        _locationState.value = _locationState.value.copy(
                            locationState = Response.Error(
                                message ?: Message.UnableToRetrieveWeather
                            ),
                        )
                    }
                )
            }
        }
    }

    fun searchCity(name: String) {
        viewModelScope.launch {
            _citiesState.value = emptyList()
            _searchQuery.value = name
            locationRepository.searchCity(name).fold(
                onSuccess = { location ->
                    _citiesState.value = location
                },
                onFailure = { error ->
                    _citiesState.value = emptyList()
                }
            )
        }
    }
}
