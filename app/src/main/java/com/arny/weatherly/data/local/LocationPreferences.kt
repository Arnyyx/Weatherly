package com.arny.weatherly.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.arny.weatherly.domain.model.Location
import com.arny.weatherly.domain.model.WeatherResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "weather_prefs")

private object PreferencesKeys {
    val WARD = stringPreferencesKey("ward")
    val CITY = stringPreferencesKey("city")
    val COUNTRY = stringPreferencesKey("country")
    val FULL_ADDRESS = stringPreferencesKey("full_address")
    val LATITUDE = doublePreferencesKey("latitude")
    val LONGITUDE = doublePreferencesKey("longitude")
    val WEATHER_DATA = stringPreferencesKey("weather_data")
}

@Singleton
class LocationPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val gson = Gson()
    }

    suspend fun saveLocation(location: Location) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.WARD] = location.ward
            prefs[PreferencesKeys.CITY] = location.city
            prefs[PreferencesKeys.COUNTRY] = location.country
            prefs[PreferencesKeys.FULL_ADDRESS] = location.fullAddress
            prefs[PreferencesKeys.LATITUDE] = location.latitude
            prefs[PreferencesKeys.LONGITUDE] = location.longitude
        }
    }

    suspend fun saveWeather(weather: WeatherResponse) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.WEATHER_DATA] = gson.toJson(weather)
        }
    }

    val locationFlow: Flow<Location?> = context.dataStore.data.map { prefs ->
        val lat = prefs[PreferencesKeys.LATITUDE]
        val lon = prefs[PreferencesKeys.LONGITUDE]
        val ward = prefs[PreferencesKeys.WARD]
        val city = prefs[PreferencesKeys.CITY]
        val country = prefs[PreferencesKeys.COUNTRY]
        val fullAddress = prefs[PreferencesKeys.FULL_ADDRESS]
        if (lat != null && lon != null && ward != null && city != null && country != null && fullAddress != null) {
            Location(
                ward = ward,
                city = city,
                country = country,
                fullAddress = fullAddress,
                latitude = lat,
                longitude = lon
            )
        } else {
            null
        }
    }

    val weatherFlow: Flow<WeatherResponse?> = context.dataStore.data.map { prefs ->
        prefs[PreferencesKeys.WEATHER_DATA]?.let {
            gson.fromJson(it, WeatherResponse::class.java)
        }
    }
}