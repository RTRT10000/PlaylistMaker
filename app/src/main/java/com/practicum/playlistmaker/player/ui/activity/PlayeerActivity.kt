package com.practicum.playlistmaker.player.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.player.domain.state.PlayeerState
import com.practicum.playlistmaker.player.ui.view_model.PlayeerViewModel
import com.practicum.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.android.ext.android.inject


class PlayeerActivity : AppCompatActivity() {


   companion object {
        const val TRACK = "track"
    }




    private lateinit var btnPause: ImageButton
    private lateinit var btnPlay: ImageButton
    private lateinit var trackTimeMillis: TextView


    private lateinit var  viewModel: PlayeerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        val gson: Gson by inject()


        val playerArrowBack: ImageButton = findViewById(R.id.backButton)
        val cover: ImageView = findViewById(R.id.cover)
        val trackName: TextView = findViewById(R.id.tvTrackName)
        val artistName: TextView = findViewById(R.id.tvArtistName)
        val trackTime: TextView = findViewById(R.id.trackTimeMillisFull)
        val collectionName: TextView = findViewById(R.id.collectionName)
        val releaseDate: TextView = findViewById(R.id.releaseDate)
        val primaryGenreName = findViewById<TextView>(R.id.primaryGenreName)
        val country = findViewById<TextView>(R.id.country)

        trackTimeMillis = findViewById(R.id.trackTimeMillis)




        btnPlay = findViewById(R.id.playButton)
        btnPause = findViewById(R.id.pauseButton)


       playerArrowBack.setOnClickListener {
            this.finish()
        }

        val intent = getIntent()
        val json = intent.getStringExtra(TRACK).toString()


        val track: Track = gson.fromJson(json, Track::class.java)

        val viewModel: PlayeerViewModel by viewModel {
            parametersOf(track.previewUrl)
        }

        this.viewModel = viewModel


       val artworkUrl512 = track.getCoverArtwork()
        Glide.with(applicationContext)
            .load(artworkUrl512)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(8f,this.applicationContext)))
            .into(cover)

        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text = track.getFormatTimeMillis()
        collectionName.text = track.collectionName
        releaseDate.text = track.releaseDate

        primaryGenreName.text = track.primaryGenreName
        country.text = track.country

        viewModel.getPlayeerStateLiveData().observe(this) {state ->
            render(state)
        }

       btnPlay.setOnClickListener {
            viewModel.playPlayer()
        }
        btnPause.setOnClickListener {
            viewModel.pausePlayer()
        }
    }


    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroyPlayeer()
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }

    private fun render(state: PlayeerState) {
       when (state) {
           is PlayeerState.PreparePlayeer -> showPrepare()
           is PlayeerState.PausePlayeer -> showPause()
           is PlayeerState.PlayPlayer -> showPlay(state.time)
           is PlayeerState.CompletePlay -> showComplete()
           is PlayeerState.StartPlayeer -> showStart()
       }
    }

    private fun showPrepare() {
        btnPlay.isEnabled = true
    }

    private fun showComplete() {
        btnPlay.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        trackTimeMillis.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)
    }

    private fun showPause() {
        btnPlay.visibility = View.GONE
        btnPause.visibility = View.VISIBLE
    }

   private fun showPlay(time: String) {
       trackTimeMillis.text = time
   }

   private fun showStart() {
       btnPause.visibility = View.VISIBLE
       btnPlay.visibility = View.GONE
   }


}



