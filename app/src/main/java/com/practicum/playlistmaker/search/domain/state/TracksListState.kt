package com.practicum.playlistmaker.search.domain.state

import com.practicum.playlistmaker.search.domain.models.Track

sealed interface TracksListState {
    object Loading : TracksListState
    data class Input(val isInputNullOrEmpty: Boolean) : TracksListState
    object HistoryTrackList : TracksListState
    data class Error(val data: String?) : TracksListState
    object EmptyContentTracks : TracksListState
    object ContentTracks : TracksListState
}