package com.practicum.playlistmaker.settings.domain.impl

import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository) :
    SettingsInteractor {
    override fun loadDarkChecked(): Boolean {
        return settingsRepository.getDarkThemeSettings()
    }

    override fun saveDarkChecked(checked: Boolean) {
        settingsRepository.putDarkThemeSettings(checked)
    }

}




