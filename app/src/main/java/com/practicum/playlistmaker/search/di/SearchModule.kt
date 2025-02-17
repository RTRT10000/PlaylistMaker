package com.practicum.playlistmaker.search.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.practicum.playlistmaker.search.data.NetworkClient
import com.practicum.playlistmaker.search.data.StoragePreferences
import com.practicum.playlistmaker.search.data.impl.HistoryTracksListRepositoryImpl
import com.practicum.playlistmaker.search.data.impl.StoragePreferencesImp
import com.practicum.playlistmaker.search.data.impl.TracksRepositoryImpl
import com.practicum.playlistmaker.search.data.network.ItunesApi
import com.practicum.playlistmaker.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.search.domain.api.HistoryTracksListRepository
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.api.TracksRepository
import com.practicum.playlistmaker.search.domain.impl.SearchHistoryInteractor
import com.practicum.playlistmaker.search.domain.impl.TracksInteractorImpl
import com.practicum.playlistmaker.search.ui.view_model.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val PLAYLIST_PREFERENCES = "playlist_preferences"

val searchModule = module {

    viewModel {
        SearchViewModel(get(),get())
    }

    single {
        SearchHistoryInteractor(get(),get())
    }

    single<TracksInteractor> {
         TracksInteractorImpl(get())
    }

    single<TracksRepository> {
        TracksRepositoryImpl(get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }

    single<ItunesApi> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesApi::class.java)
    }

    single {
        Handler(Looper.getMainLooper())
    }

    single<HistoryTracksListRepository> {
        HistoryTracksListRepositoryImpl(get(), get())
    }

    factory {
        Gson()
    }

    single<StoragePreferences> {
        StoragePreferencesImp(get())
    }

    factory {
        androidContext()
            .getSharedPreferences(PLAYLIST_PREFERENCES, Context.MODE_PRIVATE)
    }




}






