package com.practicum.playlistmaker.player.ui.view_model

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.player.domain.api.PlayerInteractor
import com.practicum.playlistmaker.player.domain.state.PlayeerState
import com.practicum.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale


class PlayeerViewModel(
    application: Application,
    private val playeerInteractor: PlayerInteractor,
    private val previewUrl: String
) : AndroidViewModel(application) {

    private var mainThreadHandler = Handler(Looper.getMainLooper())
    private val updatePlayTimeRunnable = updatePlayTime()

    init {
        preparePlayer(previewUrl)
        mainThreadHandler = android.os.Handler(Looper.getMainLooper())
    }


    private val playeerStateLiveData = MutableLiveData<PlayeerState>()
    fun getPlayeerStateLiveData(): LiveData<PlayeerState> = playeerStateLiveData


    companion object {
        fun getViewModelFactory(previewUrl: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayeerViewModel(
                    this[APPLICATION_KEY] as Application,
                    Creator.getPlayerInteractor(),
                    previewUrl,
                )
            }
        }

        private const val DELAY = 300L
    }

    private fun preparePlayer(previewUrl: String) {

        val onPrepare = {
            playeerStateLiveData.postValue(PlayeerState.PreparePlayeer)
        }

        val onComplete = {
            mainThreadHandler?.removeCallbacks(updatePlayTimeRunnable)
            playeerStateLiveData.postValue(PlayeerState.CompletePlay)
        }

        playeerInteractor.preparePlayer(previewUrl, onPrepare, onComplete)
    }


    fun playPlayer() {
        playeerStateLiveData.postValue(PlayeerState.StartPlayeer)
        mainThreadHandler?.post(updatePlayTimeRunnable)
        playeerInteractor.startPlayer()


    }

    fun pausePlayer() {
        playeerInteractor.pausePlayer()
        mainThreadHandler?.removeCallbacks(updatePlayTimeRunnable)
        playeerStateLiveData.postValue(PlayeerState.PausePlayeer)
    }


    private fun updatePlayTime(): Runnable {
        return object : Runnable {
            override fun run() {
                playeerStateLiveData.postValue(PlayeerState.PlayPlayer(playeerInteractor.getPlayTime()))
                mainThreadHandler?.postDelayed(this, DELAY)
            }
        }
    }

    fun destroyPlayeer() {
        mainThreadHandler?.removeCallbacks(updatePlayTimeRunnable)
        playeerInteractor.releasePlayer()
    }

}




/*private fun pausePlayer() {

    playerInteractor.pausePlayer()
    btnPlay.visibility = View.VISIBLE
    btnPause.visibility = View.GONE
    mainThreadHandler?.removeCallbacks(updatePlayTimeRunnable)
}*/


/*
private fun updatePlayTime(): Runnable {
    return object : Runnable {
        override fun run() {
            trackTimeMillis.text = playerInteractor.getPlayTime()
            mainThreadHandler?.postDelayed(this, DELAY)
        }
    }
}
*/


