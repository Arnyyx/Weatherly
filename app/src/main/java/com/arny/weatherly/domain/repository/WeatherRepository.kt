package com.arny.weatherly.domain.repository

import com.arny.weatherly.domain.model.Location
import com.arny.weatherly.domain.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.Url

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherResponse>
}