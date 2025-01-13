package com.practicum.playlistmaker.data.impl

import com.practicum.playlistmaker.data.StoragePreferences
import com.practicum.playlistmaker.domain.api.SettingsRepository

class SettingsRepositoryImpl(private val storagePreferences: StoragePreferences) : SettingsRepository {

    override fun  getDarkThemeSettings(): Boolean {
        return storagePreferences.getBooleanFromStorage()
    }

    override  fun putDarkThemeSettings(checked: Boolean) {
        storagePreferences.putBooleanToStorage(checked)
    }

}




