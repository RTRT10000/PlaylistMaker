package com.practicum.playlistmaker.settings.data

interface SettingsStoragePreferences {
    fun getBooleanFromStorage(): Boolean
    fun putBooleanToStorage(value: Boolean)
}

