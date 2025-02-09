package com.practicum.playlistmaker.player.data.impl

import android.media.MediaPlayer
import com.practicum.playlistmaker.player.domain.api.PlayerRepository
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerRepositoryImpl : PlayerRepository {

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }

    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT



    override fun preparePlayer(previewUrl: String, onPrepare: () -> Unit, onComplete: () -> Unit) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
            onPrepare()
        }
        mediaPlayer.setOnCompletionListener{
            onComplete()
            playerState = STATE_PREPARED
       }
    }

    override fun getPlayTime(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
    }

    override fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }





}