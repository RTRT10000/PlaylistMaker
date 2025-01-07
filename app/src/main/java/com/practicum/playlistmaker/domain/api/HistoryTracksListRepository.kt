package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.StoragePreferences
import com.practicum.playlistmaker.domain.models.Track

interface HistoryTracksListRepository {

    fun getHistoryTrackList(): ArrayList<Track>
    fun putHistoryTrackList(trackList: ArrayList<Track>)
    fun clearHistoryTrackList()

}