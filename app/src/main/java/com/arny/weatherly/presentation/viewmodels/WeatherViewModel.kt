package com.arny.weatherly.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arny.weatherly.domain.repository.WeatherRepository
import com.arny.weatherly.presentation.states.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private var _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun getWeather(
        latitude: Double,
        longitude: Double
    ) {
        _weatherState.value = WeatherState.Loading
        viewModelScope.launch {
            weatherRepository.getWeather(latitude, longitude).onSuccess {
                _weatherState.value = WeatherState.Success(it)
            }.onFailure {
                _weatherState.value = WeatherState.Error(it.message ?: "Unable to retrieve weather")
            }
        }
    }
}