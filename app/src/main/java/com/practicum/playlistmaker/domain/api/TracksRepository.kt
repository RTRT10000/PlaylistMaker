package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.DDomainTracksResponse


interface TracksRepository {
    fun searchTracks(expression: String): DDomainTracksResponse
}