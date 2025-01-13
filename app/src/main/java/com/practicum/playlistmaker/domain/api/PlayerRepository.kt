package com.practicum.playlistmaker.domain.api




interface PlayerRepository {
    fun preparePlayer(previewUrl: String, onPrepare: () -> Unit, onComplete: () -> Unit)
    fun getPlayTime(): String
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()
}





