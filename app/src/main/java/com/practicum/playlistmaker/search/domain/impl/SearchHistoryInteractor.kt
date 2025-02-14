package com.practicum.playlistmaker.search.domain.impl

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.player.ui.activity.PlayeerActivity
import com.practicum.playlistmaker.search.domain.api.OnTrackItemClickListener

class SearchHistoryInteractor() : OnTrackItemClickListener {


    companion object {
        private const val CLOCK_DEBOUNCE_DELAY = 1000L
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true
    val historyTracksListRepository = Creator.getHistoryTracksListRepository()



    override fun onTrackItemClick(trackItem: Track, context: Context) {

       if (clickDebounce()) {
           val trackList = historyTracksListRepository.getHistoryTrackList()

           if (trackList.isEmpty()) {
               trackList.add(trackItem)
               historyTracksListRepository.putHistoryTrackList(trackList)
           } else {
               processItem(trackItem, trackList)
           }
           val json = Gson().toJson(trackItem)
           val playeerIntent = Intent(context, PlayeerActivity::class.java)
           playeerIntent.putExtra("track", json)
           context.startActivity(playeerIntent)
       }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed( {isClickAllowed = true}, CLOCK_DEBOUNCE_DELAY)
        }
        return current
    }

    fun processItem(trackItem: Track, trackList: ArrayList<Track>) {
        val index = trackList.indexOfFirst { it.trackId == trackItem.trackId }
        if (index >= 0) {
            trackList.removeAt(index)
            trackList.add(0,trackItem)
        } else {
            if (trackList.size == 10) {
                trackList.removeAt(9)
                trackList.add(0,trackItem)
            } else trackList.add(0,trackItem)
        }

        historyTracksListRepository.putHistoryTrackList(trackList)
    }

   fun getHistoryTrackList(): ArrayList<Track> {
       return historyTracksListRepository.getHistoryTrackList()
    }



}





