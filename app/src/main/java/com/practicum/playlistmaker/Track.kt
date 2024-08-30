package com.practicum.playlistmaker

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String,
    val artworkUrl100: String,
    val trackId: Int,
    val collectionName: String,
    val releaseDate: Int,
    val primaryGenreName: String,
    val country: String,
)
