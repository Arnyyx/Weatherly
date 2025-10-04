package com.arny.weatherly.domain.repository

import com.arny.weatherly.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): Flow<Result<Weather>>
}