package com.practicum.playlistmaker.domain.impl

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.ui.main.PLAYLIST_PREFERENCES
import com.practicum.playlistmaker.ui.main.SEARCH_HISTORY_LIST
import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.ui.player.PlayeerActivity

class SearchHistory(
    val context: Context
): OnTrackItemClickListener {

    //val sharedPref = context.getSharedPreferences(PLAYLIST_PREFERENCES, AppCompatActivity.MODE_PRIVATE)


    companion object {
        private const val CLOCK_DEBOUNCE_DELAY = 1000L
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true
    val historyTracksListRepository = Creator.getHistoryTracksListRepository()


    /*fun getHistoryTrackList(): ArrayList<Track> {
        val json = sharedPref.getString(SEARCH_HISTORY_LIST, null) ?: return ArrayList<Track>()
        return arrayToList(Gson().fromJson(json, Array<Track>::class.java))
    }


    fun putHistoryTrackList(tracksList: ArrayList<Track>) {
        val json = Gson().toJson(tracksList)
        sharedPref.edit()
            .putString(SEARCH_HISTORY_LIST, json)
            .apply()
    }

   fun clearHistoryList() {
       sharedPref.edit()
           .remove(SEARCH_HISTORY_LIST)
           .apply()
   }*/

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

   /*fun arrayToList(inArray: Array<Track>): ArrayList<Track> {
       val outArrayList = ArrayList<Track>()
       inArray.forEach { track ->
           outArrayList.add(track)
           Log.d("check_click", "item $track")
       }
       return outArrayList
   }*/

}

 interface OnTrackItemClickListener {
    fun onTrackItemClick(trackItem: Track, context: Context)
}

