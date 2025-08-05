package com.arny.weatherly.di

import android.content.Context
import com.arny.weatherly.data.remote.LocationRepositoryImpl
import com.arny.weatherly.data.remote.WeatherRepositoryImpl
import com.arny.weatherly.domain.repository.LocationRepository
import com.arny.weatherly.domain.repository.WeatherRepository
import com.arny.weatherly.network.OpenWeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository {
        return LocationRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(apiService: OpenWeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOpenWeatherApiService(): OpenWeatherApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/3.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApiService::class.java)
    }
}