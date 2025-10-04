package com.arny.weatherly.data.remote.service

import com.arny.weatherly.BuildConfig
import com.arny.weatherly.data.remote.dto.LocationDto
import com.arny.weatherly.data.remote.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("data/3.0/onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appid: String = BuildConfig.API_KEY,
        @Query("units") units: String = "metric",
    ): Response<WeatherDto>

    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<List<LocationDto>>
}