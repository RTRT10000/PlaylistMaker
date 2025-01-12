package com.practicum.playlistmaker.domain.api

interface SettingsInteractor {
    fun loadDarkChecked(): Boolean

    fun saveDarkChecked(checked: Boolean)

}