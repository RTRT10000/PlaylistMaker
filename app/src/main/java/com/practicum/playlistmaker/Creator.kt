package com.practicum.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import com.practicum.playlistmaker.data.StoragePreferences
import com.practicum.playlistmaker.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.data.impl.StoragePreferencesImp
import com.practicum.playlistmaker.data.impl.TracksRepositoryImpl
import com.practicum.playlistmaker.domain.api.HistoryTracksListRepository
import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.data.impl.HistoryTracksListRepositoryImpl
import com.practicum.playlistmaker.domain.impl.TracksInteractorImpl
import com.practicum.playlistmaker.ui.main.PLAYLIST_PREFERENCES

object Creator {

    private lateinit var application: Application

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
}