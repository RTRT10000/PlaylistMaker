package com.practicum.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker.data.StoragePreferences
import com.practicum.playlistmaker.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.data.impl.StoragePreferencesImp
import com.practicum.playlistmaker.data.impl.TracksRepositoryImpl
import com.practicum.playlistmaker.domain.api.HistoryTracksListRepository
import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.data.impl.HistoryTracksListRepositoryImpl
import com.practicum.playlistmaker.data.impl.PlayerRepositoryImpl
import com.practicum.playlistmaker.data.impl.SettingsRepositoryImpl
import com.practicum.playlistmaker.domain.api.PlayerInteractor
import com.practicum.playlistmaker.domain.api.PlayerRepository
import com.practicum.playlistmaker.domain.api.SettingsInteractor
import com.practicum.playlistmaker.domain.api.SettingsRepository
import com.practicum.playlistmaker.domain.impl.PlayerInteractorImpl
import com.practicum.playlistmaker.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.domain.impl.TracksInteractorImpl
import com.practicum.playlistmaker.ui.main.PLAYLIST_PREFERENCES

object Creator {

    private lateinit var application: Application
    private val gson = Gson()

    fun initApplication(application: Application) {
        this.application = application
    }

    fun provideSharedPrefernces(): SharedPreferences {
        return application.getSharedPreferences(PLAYLIST_PREFERENCES, Application.MODE_PRIVATE)
    }

    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }

    fun getTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }

    fun getStoragePreferences(): StoragePreferences {
        return StoragePreferencesImp(provideSharedPrefernces())
    }

    fun getHistoryTracksListRepository(): HistoryTracksListRepository {
         return HistoryTracksListRepositoryImpl(getStoragePreferences())
    }

    fun getGson(): Gson {
        return gson
    }

    fun getSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl(getStoragePreferences())
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