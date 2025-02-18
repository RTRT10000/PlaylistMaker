package com.practicum.playlistmaker.sharing.di

import android.app.Application
import com.practicum.playlistmaker.settings.data.SettingsStoragePreferences
import com.practicum.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.practicum.playlistmaker.settings.data.impl.SettingsStoragePreferencesImpl
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.sharing.data.impl.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.api.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val shearingModule = module {


    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }

    single {
        ExternalNavigator((androidContext() as Application))
    }



}