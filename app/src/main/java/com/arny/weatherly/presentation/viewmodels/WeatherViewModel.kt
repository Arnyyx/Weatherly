package com.arny.weatherly.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arny.weatherly.domain.repository.WeatherRepository
import com.arny.weatherly.presentation.states.Message
import com.arny.weatherly.presentation.states.WeatherState
import com.arny.weatherly.presentation.states.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _weatherState.value = WeatherState(weatherState = Response.Loading)
            weatherRepository.getWeather(latitude, longitude).catch { error ->
                _weatherState.value = WeatherState(
                    weatherState = Response.Error(Message.UnableToRetrieveWeather)
                )
            }.collect { result ->
                result.fold(
                    onSuccess = { weather ->
                        _weatherState.value =
                            WeatherState(weatherState = Response.Success(weather))
                    },
                    onFailure = { error ->
                        _weatherState.value = WeatherState(
                            weatherState = Response.Error(Message.UnableToRetrieveWeather)
                        )
                    }
                )
            }
        }
    }
}