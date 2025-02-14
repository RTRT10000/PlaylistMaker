package com.practicum.playlistmaker.settings.domain.api



interface SettingsRepository {
    fun getDarkThemeSettings(): Boolean
    fun putDarkThemeSettings(checked: Boolean)
}