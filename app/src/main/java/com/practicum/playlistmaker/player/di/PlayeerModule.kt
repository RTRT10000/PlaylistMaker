package com.practicum.playlistmaker.player.di

import android.media.MediaPlayer
import com.practicum.playlistmaker.player.data.impl.PlayerRepositoryImpl
import com.practicum.playlistmaker.player.domain.api.PlayerInteractor
import com.practicum.playlistmaker.player.domain.api.PlayerRepository
import com.practicum.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.practicum.playlistmaker.player.ui.view_model.PlayeerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playeerModule = module {

    viewModel { (previewUrl: String) ->
        PlayeerViewModel(get(), previewUrl)
    }

    single<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }

    single<PlayerRepository> {
       PlayerRepositoryImpl(get())
    }

    single {
        MediaPlayer ()
    }

}