package com.arny.weatherly.data.remote

import android.util.Log
import com.arny.weatherly.domain.model.WeatherResponse
import com.arny.weatherly.domain.repository.WeatherRepository
import com.arny.weatherly.network.OpenWeatherApiService
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: OpenWeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeather(latitude, longitude)
            if (response.isSuccessful) {
                response.body()?.let { weather ->
                    Result.success(weather)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("API request failed with code ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather: ${e.message}")
            Result.failure(e)
        }
    }
}