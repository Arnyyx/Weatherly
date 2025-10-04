package com.arny.weatherly.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arny.weatherly.domain.model.WeatherUnit
import com.arny.weatherly.domain.model.UserSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore("settings")

@Singleton
class UserSettingsDataStore(@ApplicationContext private val context: Context) {
    private val UNIT_KEY = stringPreferencesKey("temperature")

    val settingsFlow: Flow<UserSettings> = context.settingsDataStore.data.map { prefs ->
        UserSettings(
            weatherUnit = prefs[UNIT_KEY]?.let { WeatherUnit.valueOf(it) } ?: WeatherUnit.METRIC,
        )
    }

    suspend fun saveSettings(settings: UserSettings) {
        context.settingsDataStore.edit { prefs ->
            prefs[UNIT_KEY] = settings.weatherUnit.name
        }
    }
}
