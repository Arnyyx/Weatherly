package com.arny.weatherly.data.remote

import com.arny.weatherly.data.local.SettingsManager
import com.arny.weatherly.data.local.dao.WeatherDao
import com.arny.weatherly.data.local.entity.toDomain
import com.arny.weatherly.data.remote.dto.toDomain
import com.arny.weatherly.data.remote.service.OpenWeatherApiService
import com.arny.weatherly.domain.model.Weather
import com.arny.weatherly.domain.model.WeatherUnit
import com.arny.weatherly.domain.model.toEntity
import com.arny.weatherly.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: OpenWeatherApiService,
    private val weatherDao: WeatherDao,
    private val settingsManager: SettingsManager
) : WeatherRepository {
    companion object {
        private const val TTL_DURATION_MS = 30 * 60 * 1000L // 30 phút
    }

    override suspend fun getWeather(latitude: Double, longitude: Double): Flow<Result<Weather>> =
        flow {
            val settings = settingsManager.getCurrentSettings()
            val units = when (settings.weatherUnit) {
                WeatherUnit.METRIC -> "metric"
                WeatherUnit.IMPERIAL -> "imperial"
                else -> "standard"
            }
            val currentTime = System.currentTimeMillis()
            // Emit cache đầu tiên
            weatherDao.getByLocation(latitude, longitude, currentTime)?.let { cached ->
                emit(Result.success(cached.toDomain()))
            } ?: emit(Result.failure(Exception("No cached data")))

            // Gọi API song song để cập nhật
            try {
                val response = apiService.getWeather(latitude, longitude, units)
                if (response.isSuccessful) {
                    response.body()?.let { response ->
                        val weather = response.toDomain()
                        weatherDao.insert(
                            weather.toEntity().copy(ttl = currentTime + TTL_DURATION_MS)
                        )
                        emit(Result.success(weather))
                    } ?: emit(Result.failure(Exception("Empty response body")))
                } else {
                    emit(Result.failure(Exception("API request failed with code ${response.code()}")))
                }
            } catch (e: Exception) {
                emit(Result.failure(Exception("Error fetching weather: ${e.message}")))
            }
        }.flowOn(Dispatchers.IO)
}