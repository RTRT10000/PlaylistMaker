package com.practicum.playlistmaker.player.domain.api




interface PlayerRepository {
    fun preparePlayer(previewUrl: String, onPrepare: () -> Unit, onComplete: () -> Unit)
    fun getPlayTime(): String
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()
}





