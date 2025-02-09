package com.practicum.playlistmaker.search.domain.api

import android.content.Context
import com.practicum.playlistmaker.search.domain.models.Track

interface OnTrackItemClickListener {
    fun onTrackItemClick(trackItem: Track, context: Context)
}

