package com.arny.weatherly.data.remote

import com.arny.weatherly.data.local.datastore.UserSettingsDataStore
import com.arny.weatherly.domain.model.UserSettings
import com.arny.weatherly.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
    private val userSettingsDataStore: UserSettingsDataStore
) : UserSettingsRepository {
    override fun getUserSettings(): Flow<UserSettings> = userSettingsDataStore.settingsFlow
    override suspend fun updateUserSettings(settings: UserSettings) =
        userSettingsDataStore.saveSettings(settings)
}
