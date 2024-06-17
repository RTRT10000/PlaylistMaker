package com.practicum.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson

class SearchHistory(
    val sharedPref: SharedPreferences
) {


    fun getHistoryTrackList(): Array<Track> {
        val json = sharedPref.getString(SEARCH_HISTORY_LIST, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    fun putHistoryTrackList(tracksList: Array<Track>) {
        val json = Gson().toJson(tracksList)
        sharedPref.edit()
            .putString(SEARCH_HISTORY_LIST, json)
            .apply()
    }

}