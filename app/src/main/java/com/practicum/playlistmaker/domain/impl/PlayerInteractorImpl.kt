package com.practicum.playlistmaker.domain.impl

import android.content.Intent
import android.media.MediaPlayer
import android.view.View
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.api.PlayerInteractor
import com.practicum.playlistmaker.domain.api.PlayerRepository




class PlayerInteractorImpl(private val playerRepository: PlayerRepository) : PlayerInteractor {



    override fun preparePlayer(previewUrl: String, onPrepare: () -> Unit, onComplete: () -> Unit) {
        playerRepository.preparePlayer(previewUrl, onPrepare, onComplete)
    }

    override fun getPlayTime(): String {
       return playerRepository.getPlayTime()
    }

    override fun startPlayer() {
        playerRepository.startPlayer()
    }

    override fun pausePlayer() {
        playerRepository.pausePlayer()
    }

    override fun releasePlayer() {
        playerRepository.releasePlayer()
    }

}



