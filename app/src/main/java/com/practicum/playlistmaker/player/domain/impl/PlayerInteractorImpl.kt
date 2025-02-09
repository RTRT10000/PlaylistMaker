package com.practicum.playlistmaker.player.domain.impl

import com.practicum.playlistmaker.player.domain.api.PlayerInteractor
import com.practicum.playlistmaker.player.domain.api.PlayerRepository




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



