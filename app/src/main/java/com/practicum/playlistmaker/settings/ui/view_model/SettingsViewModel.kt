package com.practicum.playlistmaker.settings.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.sharing.domain.api.SharingInteractor
import com.practicum.playlistmaker.creator.Creator
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practicum.playlistmaker.App

class SettingsViewModel(
    application: Application,
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : AndroidViewModel(application) {

   private var themeSwitcher = MutableLiveData((getApplication<Application>() as App).darkTheme)
   fun getThemeSwitcher(): LiveData<Boolean> = themeSwitcher


    companion object {

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    this[APPLICATION_KEY] as Application,
                    Creator.getSharingInteractor(),
                    Creator.getSettingInteractor(),
                )
            }
        }
    }

    fun shareApp() {
        sharingInteractor.shareApp()
    }

    fun openTerms() {
        sharingInteractor.openTerms()
    }

    fun openSupport() {
        sharingInteractor.openSupport()
    }

    fun switchTheme(checked: Boolean) {
        (getApplication<Application>() as App).darkTheme = checked
        settingsInteractor.saveDarkChecked(checked)
        (getApplication<Application>() as App).switchTheme(checked)

    }
}





