package com.arny.weatherly.network

import com.arny.weatherly.BuildConfig
import com.arny.weatherly.domain.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appid: String = BuildConfig.API_KEY,
        @Query("units") units: String = "metric",
    ): Response<WeatherResponse>
}