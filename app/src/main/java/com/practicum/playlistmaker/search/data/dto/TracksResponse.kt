package com.practicum.playlistmaker.search.data.dto

class TracksResponse(val resultCount: String,
                     val results: List<TrackDto>) : Response()