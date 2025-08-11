package com.arny.weatherly.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arny.weatherly.data.local.LocationPreferences
import com.arny.weatherly.domain.repository.WeatherRepository
import com.arny.weatherly.presentation.states.WeatherState
import com.arny.weatherly.presentation.states.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationPrefs: LocationPreferences
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun getWeather(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            _weatherState.value = WeatherState(
                weatherState = Response.Loading
            )
            // Fetch weather from repository
            weatherRepository.getWeather(latitude, longitude).fold(
                onSuccess = { weather ->
                    _weatherState.value = WeatherState(
                        weatherState = Response.Success(weather)
                    )
                    locationPrefs.saveWeather(weather) // Cache the weather
                },
                onFailure = { error ->
                    locationPrefs.weatherFlow.first()?.let { cachedWeather ->
                        _weatherState.value = WeatherState(
                            weatherState = Response.Success(cachedWeather)
                        )
                    } ?: run {
                        _weatherState.value = WeatherState(
                            weatherState = Response.Error(
                                error.message ?: "Unable to retrieve weather"
                            )
                        )
                    }
                }
            )
        }
    }
}