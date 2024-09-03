package com.practicum.playlistmaker

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.practicum.playlistmaker.SearchActivity.Companion.INPUT_TEXT
import com.practicum.playlistmaker.SearchActivity.Companion.TEXT_DEF
import java.text.SimpleDateFormat
import java.util.Locale

class PlayeerActivity : AppCompatActivity() {

    private var json: String = ""
    companion object {
        const val INPUT_TRACK = "INPUT_TRACK"
        const val TRACK = "track"

    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        val playerArrowBack: ImageButton = findViewById(R.id.backButton)
        val cover: ImageView = findViewById(R.id.cover)
        val trackName: TextView = findViewById(R.id.tvTrackName)
        val artistName: TextView = findViewById(R.id.tvArtistName)
        val trackTime: TextView = findViewById(R.id.trackTimeMillisFull)
        val collectionName: TextView = findViewById(R.id.collectionName)
        val releaseDate: TextView = findViewById(R.id.releaseDate)
        val primaryGenreName = findViewById<TextView>(R.id.primaryGenreName)
        val country = findViewById<TextView>(R.id.country)

        playerArrowBack.setOnClickListener {
            this.finish()
        }

        val intent = getIntent()
        json = intent.getStringExtra(TRACK).toString()
        val track: Track = Gson().fromJson(json,Track::class.java)


        val artworkUrl512 = track.getCoverArtwork()
        Glide.with(applicationContext)
            .load(artworkUrl512)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(8f,this.applicationContext)))
            .into(cover)

        trackName.text = track.trackName
        artistName.text = track.artistName
        if (track.trackTimeMillis.length > 0) {
            trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toLong())
        }
        collectionName.text = track.collectionName
        releaseDate.text = track.releaseDate.toString()
        primaryGenreName.text = track.primaryGenreName
        country.text = track.country
    }






}

