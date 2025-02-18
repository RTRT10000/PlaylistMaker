package com.practicum.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.player.di.playeerModule
import com.practicum.playlistmaker.search.di.searchModule
import com.practicum.playlistmaker.settings.di.settingsModule
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.sharing.di.shearingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.android.ext.android.inject

class App : Application() {

    var darkTheme = false


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(searchModule, settingsModule, shearingModule, playeerModule)
        }


        val settingsInteractor: SettingsInteractor by inject()
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