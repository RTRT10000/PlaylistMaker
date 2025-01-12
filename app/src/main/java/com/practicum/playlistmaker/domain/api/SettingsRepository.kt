package com.practicum.playlistmaker.domain.api



interface SettingsRepository {
    fun getDarkThemeSettings(): Boolean
    fun putDarkThemeSettings(checked: Boolean)
}