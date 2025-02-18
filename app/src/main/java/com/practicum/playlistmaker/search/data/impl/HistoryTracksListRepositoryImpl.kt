package com.practicum.playlistmaker.search.data.impl

import com.google.gson.Gson
import com.practicum.playlistmaker.search.data.StoragePreferences
import com.practicum.playlistmaker.search.domain.api.HistoryTracksListRepository
import com.practicum.playlistmaker.search.domain.models.Track


class HistoryTracksListRepositoryImpl(
    private val storagePreferences: StoragePreferences,
    private val gson: Gson
) : HistoryTracksListRepository {

    override fun getHistoryTrackList(): ArrayList<Track> {
        val json = storagePreferences.getStringFromStorage()
        if (json.isNullOrEmpty()) {
            return ArrayList<Track>()
        } else {
            return arrayToList(gson.fromJson(json, Array<Track>::class.java))
        }

    }

    override fun putHistoryTrackList(trackList: ArrayList<Track>) {
        val json = gson.toJson(trackList)
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