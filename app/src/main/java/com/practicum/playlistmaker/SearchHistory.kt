package com.practicum.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import android.util.Log

class SearchHistory(
    val context: Context
): OnTrackItemClickListener  {

    val sharedPref = context.getSharedPreferences(PLAYLIST_PREFERENCES, AppCompatActivity.MODE_PRIVATE)


    fun getHistoryTrackList(): ArrayList<Track> {
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
   }

    override fun onTrackItemClick(trackItem: Track) {
        val  trackList = getHistoryTrackList()
        if(trackList.isEmpty()) {
            trackList.add(trackItem)
            putHistoryTrackList(trackList)
        } else {
            processItem(trackItem, trackList)
        }

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

        putHistoryTrackList(trackList)
    }

   fun arrayToList(inArray: Array<Track>): ArrayList<Track> {
       val outArrayList = ArrayList<Track>()
       inArray.forEach { track ->
           outArrayList.add(track)
           Log.d("check_click", "item $track")
       }
       return outArrayList
   }

}

 interface OnTrackItemClickListener {
    fun onTrackItemClick(trackItem: Track)
}

