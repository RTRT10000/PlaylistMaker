package com.practicum.playlistmaker.player.domain.state

import com.practicum.playlistmaker.search.domain.models.Track

sealed interface  PlayeerState {
    object PreparePlayeer : PlayeerState
    object PausePlayeer : PlayeerState
    object StartPlayeer : PlayeerState
    data class PlayPlayer(val time: String) : PlayeerState
    object CompletePlay : PlayeerState
}