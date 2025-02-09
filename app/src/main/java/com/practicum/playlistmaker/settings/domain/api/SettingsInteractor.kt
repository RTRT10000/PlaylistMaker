package com.practicum.playlistmaker.settings.domain.api

interface SettingsInteractor {
    fun loadDarkChecked(): Boolean

    fun saveDarkChecked(checked: Boolean)

}