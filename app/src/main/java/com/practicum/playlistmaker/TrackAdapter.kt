package com.practicum.playlistmaker

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TrackAdapter() : RecyclerView.Adapter<TrackViewHolder> () {

    var tracks = ArrayList<Track>()

    var onTrackItemClickListener: OnTrackItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_track_item, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            onTrackItemClickListener?.onTrackItemClick(tracks[position], holder.itemView.context)
        }
    }

    override fun getItemCount() = tracks.size

}