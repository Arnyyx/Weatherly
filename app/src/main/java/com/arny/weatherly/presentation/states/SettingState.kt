package com.arny.weatherly.presentation.states

import com.arny.weatherly.domain.model.UserSettings

data class SettingState(
    val settingState: Response<UserSettings> = Response.Idle,
)