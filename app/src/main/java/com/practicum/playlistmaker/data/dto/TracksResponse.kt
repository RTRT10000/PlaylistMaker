package com.practicum.playlistmaker.data.dto

import com.practicum.playlistmaker.domain.models.Track

class TracksResponse(val resultCount: String,
                     val results: List<TrackDto>) : Response()