package com.arny.weatherly.di

import android.content.Context
import androidx.room.Room
import com.arny.weatherly.data.local.AppDatabase
import com.arny.weatherly.data.local.dao.LocationDao
import com.arny.weatherly.data.local.dao.WeatherDao
import com.arny.weatherly.data.remote.LocationRepositoryImpl
import com.arny.weatherly.data.remote.WeatherRepositoryImpl
import com.arny.weatherly.domain.repository.LocationRepository
import com.arny.weatherly.domain.repository.WeatherRepository
import com.arny.weatherly.data.remote.service.OpenWeatherApiService
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
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        apiService: OpenWeatherApiService,
        locationDao: LocationDao
    ): LocationRepository {
        return LocationRepositoryImpl(context, apiService, locationDao)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        apiService: OpenWeatherApiService,
        weatherDao: WeatherDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService, weatherDao)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOpenWeatherApiService(): OpenWeatherApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: AppDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Singleton
    @Provides
    fun provideLocationDao(database: AppDatabase): LocationDao {
        return database.locationDao()
    }
}
