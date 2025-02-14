package com.practicum.playlistmaker.player.domain.api

interface PlayerInteractor {

    fun preparePlayer(previewUrl: String, onPrepare: () -> Unit, onComplete: () -> Unit)
    fun getPlayTime(): String
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()





}