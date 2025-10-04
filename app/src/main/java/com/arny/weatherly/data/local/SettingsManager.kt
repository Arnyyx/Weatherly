package com.arny.weatherly.data.local

import com.arny.weatherly.domain.model.UserSettings
import com.arny.weatherly.domain.repository.UserSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) {
    private var currentSettings: UserSettings = UserSettings()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        // collect để giữ bản copy mới nhất trong memory
        scope.launch {
            userSettingsRepository.getUserSettings().collect {
                currentSettings = it
            }
        }
    }

    fun getCurrentSettings(): UserSettings = currentSettings

    suspend fun reload() {
        userSettingsRepository.getUserSettings()
            .firstOrNull()?.let { currentSettings = it }
    }
}
