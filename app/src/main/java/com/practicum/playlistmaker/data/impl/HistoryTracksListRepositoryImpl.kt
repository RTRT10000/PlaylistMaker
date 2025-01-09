package com.practicum.playlistmaker.data.impl

import android.util.Log
import com.google.gson.Gson
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.data.StoragePreferences
import com.practicum.playlistmaker.domain.api.HistoryTracksListRepository
import com.practicum.playlistmaker.domain.models.Track


class HistoryTracksListRepositoryImpl(private val storagePreferences: StoragePreferences) : HistoryTracksListRepository {

    override fun getHistoryTrackList(): ArrayList<Track> {
        val json = storagePreferences.getStringFromStorage()
        if (json.isNullOrEmpty()) {
            return ArrayList<Track>()
        } else {
            return arrayToList(Creator.getGson().fromJson(json, Array<Track>::class.java))
        }

    }

    override fun putHistoryTrackList(trackList: ArrayList<Track>) {
        val json = Creator.getGson().toJson(trackList)
        storagePreferences.putStringToStorage(json)
    }

    override fun clearHistoryTrackList() {
        storagePreferences.clearStorage()
    }

    fun arrayToList(inArray: Array<Track>): ArrayList<Track> {
        val outArrayList = ArrayList<Track>()
        inArray.forEach { track ->
            outArrayList.add(track)
        }
        return outArrayList
    }

}