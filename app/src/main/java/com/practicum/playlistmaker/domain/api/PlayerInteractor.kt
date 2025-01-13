package com.practicum.playlistmaker.domain.api

import android.content.Intent
import android.media.MediaPlayer
import com.practicum.playlistmaker.domain.models.Track

interface PlayerInteractor {

    fun preparePlayer(previewUrl: String, onPrepare: () -> Unit, onComplete: () -> Unit)
    fun getPlayTime(): String
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()





}