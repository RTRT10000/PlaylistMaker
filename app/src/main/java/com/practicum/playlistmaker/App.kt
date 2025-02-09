package com.practicum.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.creator.Creator


class App : Application() {

    var darkTheme = false


    override fun onCreate() {
        super.onCreate()
        Creator.initApplication(this)
        val settingsInteractor = Creator.getSettingInteractor()
        darkTheme =  settingsInteractor.loadDarkChecked()


    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}