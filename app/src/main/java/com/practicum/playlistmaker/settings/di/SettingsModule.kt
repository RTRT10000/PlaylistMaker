package com.practicum.playlistmaker.settings.di

import android.app.Application
import com.practicum.playlistmaker.settings.data.SettingsStoragePreferences
import com.practicum.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.practicum.playlistmaker.settings.data.impl.SettingsStoragePreferencesImpl
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.settings.ui.view_model.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    viewModel {
        SettingsViewModel(androidContext(), get(), get())
    }


    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    single<SettingsStoragePreferences> {
        SettingsStoragePreferencesImpl(get())
    }

}