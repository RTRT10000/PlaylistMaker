package com.practicum.playlistmaker.search.data.impl

import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.search.data.NetworkClient
import com.practicum.playlistmaker.search.data.dto.TracksRequest
import com.practicum.playlistmaker.search.data.dto.TracksResponse
import com.practicum.playlistmaker.search.domain.api.TracksRepository
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.util.Resource

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override fun searchTracks(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(TracksRequest(expression))

        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Connection Error")

            }

            200 -> {
                if ((response as TracksResponse).results.isEmpty()) {
                    Resource.Error("Tracks Not Found")
                } else {
                    Resource.Success(
                        (response as TracksResponse).results.map {
                            Track(
                                it.trackName,
                                it.artistName,
                                it.trackTimeMillis,
                                it.artworkUrl100,
                                it.trackId,
                                it.collectionName,
                                it.releaseDate,
                                it.primaryGenreName,
                                it.country,
                                it.previewUrl,
                            )
                        }
                    )
                }
            }

            else -> {
                Resource.Error("Server Error")
            }
        }
    }
}
