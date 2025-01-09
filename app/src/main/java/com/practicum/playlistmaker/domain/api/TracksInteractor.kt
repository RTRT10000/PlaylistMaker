package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.DDomainTracksResponse

interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume(foundTracks: DDomainTracksResponse)
    }
}