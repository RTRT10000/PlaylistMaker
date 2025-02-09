package com.practicum.playlistmaker.creator

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker.search.data.StoragePreferences
import com.practicum.playlistmaker.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.search.data.impl.StoragePreferencesImp
import com.practicum.playlistmaker.search.data.impl.TracksRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.HistoryTracksListRepository
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.api.TracksRepository
import com.practicum.playlistmaker.search.data.impl.HistoryTracksListRepositoryImpl
import com.practicum.playlistmaker.player.data.impl.PlayerRepositoryImpl
import com.practicum.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.practicum.playlistmaker.player.domain.api.PlayerInteractor
import com.practicum.playlistmaker.player.domain.api.PlayerRepository
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository
import com.practicum.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.search.domain.impl.TracksInteractorImpl
import com.practicum.playlistmaker.settings.data.SettingsStoragePreferences
import com.practicum.playlistmaker.sharing.data.impl.ExternalNavigator
import com.practicum.playlistmaker.settings.data.impl.SettingsStoragePreferencesImpl
import com.practicum.playlistmaker.sharing.domain.api.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import com.practicum.playlistmaker.main.ui.PLAYLIST_PREFERENCES

object Creator {

    private lateinit var application: Application
    private val gson = Gson()

    fun initApplication(application: Application) {
        Creator.application = application
    }

    fun getExternalNavigator() : ExternalNavigator {
        return ExternalNavigator(application)
    }

    fun getSharingInteractor(): SharingInteractor {
        return SharingInteractorImpl(
            externalNavigator = getExternalNavigator(),
            context = application
        )
    }

    fun provideSharedPrefernces(): SharedPreferences {
        return application.getSharedPreferences(PLAYLIST_PREFERENCES, Application.MODE_PRIVATE)
    }

    private fun getTracksRepository(context: Context): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun getTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository(application))
    }

    fun getStoragePreferences(): StoragePreferences {
        return StoragePreferencesImp(provideSharedPrefernces())
    }

    fun getSettingsStoragePreferences(): SettingsStoragePreferences {
        return SettingsStoragePreferencesImpl(provideSharedPrefernces())
    }

    fun getHistoryTracksListRepository(): HistoryTracksListRepository {
         return HistoryTracksListRepositoryImpl(getStoragePreferences())
    }

    fun getGson(): Gson {
        return gson
    }

    fun getSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl(getSettingsStoragePreferences())
    }

    fun getSettingInteractor(): SettingsInteractor {
        return SettingsInteractorImpl(getSettingsRepository())
    }

    fun getPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl()
    }

    fun getPlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository())
    }
}