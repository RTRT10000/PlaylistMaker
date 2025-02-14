package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.search.domain.models.Track

interface HistoryTracksListRepository {

    fun getHistoryTrackList(): ArrayList<Track>
    fun putHistoryTrackList(trackList: ArrayList<Track>)
    fun clearHistoryTrackList()

}