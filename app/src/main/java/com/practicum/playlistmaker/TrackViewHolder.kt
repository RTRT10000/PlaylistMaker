package com.practicum.playlistmaker

import android.view.RoundedCorner
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val trackName: TextView
    private val artistName: TextView
    private val trackTime: TextView
    private val artImage: ImageView

    init {
        trackName = itemView.findViewById(R.id.tvTrackName)
        artistName = itemView.findViewById(R.id.tvArtistName)
        trackTime = itemView.findViewById(R.id.tvTrackTime)
        artImage = itemView.findViewById(R.id.ivArtImage)
    }

    fun bind(track: Track) {
        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text = track.trackTime

        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.media)
            .centerCrop()
            .transform(RoundedCorners(10))
            .into(artImage)
    }
}




