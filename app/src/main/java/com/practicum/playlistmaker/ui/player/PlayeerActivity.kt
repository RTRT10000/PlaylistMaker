package com.practicum.playlistmaker.ui.player

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.api.PlayerInteractor
import com.practicum.playlistmaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlayeerActivity : AppCompatActivity() {


   companion object {
        const val TRACK = "track"
        private const val DELAY = 300L
    }

    private var mainThreadHandler: Handler? = null

    //private var playerState = STATE_DEFAULT

    //private var mediaPlayer = MediaPlayer()
    private lateinit var btnPause: ImageButton
    private lateinit var btnPlay: ImageButton
    private var json: String = ""
    private var  previewUrl: String = ""
    private lateinit var trackTimeMillis: TextView

    private val updatePlayTimeRunnable = updatePlayTime()

    private val playerInteractor = Creator.getPlayerInteractor()




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

        trackTimeMillis = findViewById(R.id.trackTimeMillis)




        btnPlay = findViewById(R.id.playButton)
        btnPause = findViewById(R.id.pauseButton)


       playerArrowBack.setOnClickListener {
            this.finish()
        }

        val intent = getIntent()
        val json = intent.getStringExtra(TRACK).toString()


        val track: Track = Creator.getGson().fromJson(json, Track::class.java)


       val artworkUrl512 = track.getCoverArtwork()
        Glide.with(applicationContext)
            .load(artworkUrl512)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(8f,this.applicationContext)))
            .into(cover)

        previewUrl = track.previewUrl

        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text = track.getFormatTimeMillis()
        collectionName.text = track.collectionName
        releaseDate.text = track.releaseDate

        primaryGenreName.text = track.primaryGenreName
        country.text = track.country
        mainThreadHandler = android.os.Handler(Looper.getMainLooper())

        preparePlayer()
        btnPlay.setOnClickListener {
            startPlayer()
        }
        btnPause.setOnClickListener {
            pausePlayer()
        }
    }



    private fun preparePlayer() {

       val onPrepare = {
           btnPlay.isEnabled = true
           }

        val onComplete = {
            btnPlay.visibility = View.VISIBLE
            btnPause.visibility = View.GONE
            mainThreadHandler?.removeCallbacks(updatePlayTimeRunnable)
            trackTimeMillis.text = "00:00"
        }

        playerInteractor.preparePlayer(previewUrl, onPrepare, onComplete)

        /*mediaPlayer.setDataSource(previewUrl)
       mediaPlayer.prepareAsync()
       mediaPlayer.setOnPreparedListener {
           btnPlay.isEnabled = true
           playerState = STATE_PREPARED
       }
        mediaPlayer.setOnCompletionListener {
            btnPlay.visibility = View.VISIBLE
            btnPause.visibility = View.GONE
            playerState = STATE_PREPARED
            mainThreadHandler?.removeCallbacks(updatePlayTimeRunnable)
            trackTimeMillis.text = "00:00"
        }*/


   }

    private fun startPlayer() {
        /*mediaPlayer.start()
        playerState = STATE_PLAYING*/

        playerInteractor.startPlayer()
        btnPause.visibility = View.VISIBLE
        btnPlay.visibility = View.GONE
        mainThreadHandler?.post(updatePlayTimeRunnable)
    }

    private fun pausePlayer() {
        /*mediaPlayer.pause()
        playerState = STATE_PAUSED*/

        playerInteractor.pausePlayer()
        btnPlay.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
        mainThreadHandler?.removeCallbacks(updatePlayTimeRunnable)
    }


    private fun updatePlayTime(): Runnable {
        return object : Runnable {
            override fun run() {
                //trackTimeMillis.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
                trackTimeMillis.text = playerInteractor.getPlayTime()
                mainThreadHandler?.postDelayed(this, DELAY)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainThreadHandler?.removeCallbacks(updatePlayTimeRunnable)
        playerInteractor.releasePlayer()
       // mediaPlayer.release()
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }




}

