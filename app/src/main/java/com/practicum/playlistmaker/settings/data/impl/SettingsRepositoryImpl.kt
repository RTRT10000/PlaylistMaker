package com.practicum.playlistmaker.settings.data.impl

import com.practicum.playlistmaker.settings.data.SettingsStoragePreferences
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository

class SettingsRepositoryImpl(private val settingsStoragePreferences: SettingsStoragePreferences) :
    SettingsRepository {

    override fun  getDarkThemeSettings(): Boolean {
        return settingsStoragePreferences.getBooleanFromStorage()
    }

    override  fun putDarkThemeSettings(checked: Boolean) {
        settingsStoragePreferences.putBooleanToStorage(checked)
    }

}




