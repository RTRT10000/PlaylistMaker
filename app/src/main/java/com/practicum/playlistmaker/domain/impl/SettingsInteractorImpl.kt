package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.SettingsInteractor
import com.practicum.playlistmaker.domain.api.SettingsRepository

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository) : SettingsInteractor {
    override fun loadDarkChecked(): Boolean {
        return settingsRepository.getDarkThemeSettings()
    }

    override fun saveDarkChecked(checked: Boolean) {
        settingsRepository.putDarkThemeSettings(checked)
    }

}




