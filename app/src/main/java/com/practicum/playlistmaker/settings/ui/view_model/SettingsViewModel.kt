package com.practicum.playlistmaker.settings.ui.view_model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.sharing.domain.api.SharingInteractor
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.App
import com.practicum.playlistmaker.sharing.domain.EmailData

class SettingsViewModel(
    private val context: Context,
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {

   private var themeSwitcher = MutableLiveData((context as App).darkTheme)
   fun getThemeSwitcher(): LiveData<Boolean> = themeSwitcher



    fun shareApp(shareAppLink: String) {
        sharingInteractor.shareApp(shareAppLink)
    }

    fun openTerms(termLink: String) {
        sharingInteractor.openTerms(termLink)
    }

    fun openSupport(eMailData: EmailData) {
        sharingInteractor.openSupport(eMailData)
    }

    fun switchTheme(checked: Boolean) {
        (context as App).darkTheme = checked
        settingsInteractor.saveDarkChecked(checked)
        (context as App).switchTheme(checked)

    }
}





