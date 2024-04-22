package com.practicum.playlistmaker

import android.content.Context
import android.util.TypedValue
import android.view.RoundedCorner
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale

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
        if (track.trackTimeMillis.length > 0) {
            trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toLong())
        }


        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(10f,itemView.context)))
            .into(artImage)
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }
}




