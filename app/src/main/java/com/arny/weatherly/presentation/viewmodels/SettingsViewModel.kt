package com.arny.weatherly.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arny.weatherly.data.local.SettingsManager
import com.arny.weatherly.domain.model.UserSettings
import com.arny.weatherly.domain.repository.UserSettingsRepository
import com.arny.weatherly.presentation.states.Response
import com.arny.weatherly.presentation.states.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {
    private val _userSettings = MutableStateFlow(SettingState())
    val userSettings: StateFlow<SettingState> = _userSettings.asStateFlow()

    init {
        getUserSettings()
    }

    fun getUserSettings() {
        viewModelScope.launch {
            userSettingsRepository.getUserSettings().collect { settings ->
                _userSettings.value = SettingState(
                    settingState = Response.Success(settings)
                )
            }
        }
    }

    fun onSettingsChanged(newSettings: UserSettings) {
        viewModelScope.launch {
            userSettingsRepository.updateUserSettings(newSettings)
            settingsManager.reload()
        }
    }
}
