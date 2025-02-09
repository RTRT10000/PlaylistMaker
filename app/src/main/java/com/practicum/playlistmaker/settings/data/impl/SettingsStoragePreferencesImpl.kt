package com.practicum.playlistmaker.settings.data.impl

import android.content.SharedPreferences
import com.practicum.playlistmaker.settings.data.SettingsStoragePreferences
import com.practicum.playlistmaker.main.ui.DARK_THEME

class SettingsStoragePreferencesImpl(private val sharedPref: SharedPreferences) : SettingsStoragePreferences {

    override fun getBooleanFromStorage(): Boolean {
        return sharedPref.getBoolean(DARK_THEME,false)
    }

    override fun putBooleanToStorage(checked: Boolean) {
        sharedPref.edit()
            .putBoolean(DARK_THEME,checked)
            .apply()
    }

}