package com.practicum.playlistmaker.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.data.dto.TracksResponse
import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.domain.impl.TracksInteractorImpl
import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.domain.models.domainTracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {



   // private val iTunesBaseUrl = "https://itunes.apple.com"

    /*private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()private*/

    //private val iTunesService = retrofit.create(ItunesApi::class.java)

    private lateinit var recycler: RecyclerView
    private lateinit var refreshButton: Button
    private lateinit var placeholderNotFound: ImageView
    private lateinit var placeholderConnection: ImageView
    private lateinit var inputEditText: EditText
    private lateinit var placeholderText: TextView
    private lateinit var historyRecycler: RecyclerView
    private lateinit var historyConteiner: LinearLayout
    private lateinit var btnClearHistoryList: Button
    private lateinit var searchContainer: FrameLayout

    private lateinit var progressBar: ProgressBar


    private  val trackList = ArrayList<Track>()
    private var historyTrackList = ArrayList<Track>()


    private val adapter = TrackAdapter()
    private val historyAdapter = TrackAdapter()

    private val handler = Handler(Looper.getMainLooper())
    private val handlerSearchTracks = Handler(Looper.getMainLooper())

    private lateinit var tracksInteractor: TracksInteractor


    companion object {
       const val INPUT_TEXT = "INPUT_TEXT"
       const val TEXT_DEF = ""

       private const val SEARCH_DEBOUNCE_DELAY = 2000L
   }

    private val searchRunnable = Runnable { searchRequest() }

    private var inputText: String = ""





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        placeholderNotFound = findViewById(R.id.ivPlaceHolderNotFound)
        placeholderConnection = findViewById(R.id.ivPlaceHolderConnect)
        refreshButton = findViewById(R.id.btnRefresh)
        placeholderText = findViewById(R.id.tvTextPlaceholder)
        recycler = findViewById(R.id.rvTrackList)
        historyRecycler = findViewById(R.id.rvHistoryTrackList)
        historyConteiner = findViewById(R.id.llHistoryConteiner)
        searchContainer = findViewById(R.id.flSearch)
        progressBar = findViewById(R.id.progressBar)


        val searchHistory = SearchHistory(this)
        adapter.tracks = trackList
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter.onTrackItemClickListener = searchHistory

        tracksInteractor = Creator.getTracksInteractor()

        historyTrackList = searchHistory.getHistoryTrackList()
        historyAdapter.tracks = historyTrackList
        historyRecycler.adapter = historyAdapter
        historyRecycler.layoutManager = LinearLayoutManager(this)
        btnClearHistoryList = findViewById(R.id.btnClearHistoryList)

        btnClearHistoryList.setOnClickListener {
            searchHistory.clearHistoryList()
            historyTrackList.clear()
            historyAdapter.notifyDataSetChanged()
            historyConteiner.visibility = View.GONE
        }





        inputEditText = findViewById<EditText>(R.id.inputEditText)


        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && inputEditText.text.isEmpty() && historyTrackList.isNotEmpty()) {
                historyTrackList = searchHistory.getHistoryTrackList()
                historyAdapter.tracks = historyTrackList
                historyAdapter.notifyDataSetChanged()
                historyConteiner.visibility = View.VISIBLE
                searchContainer.visibility = View.GONE

            } else {
                historyConteiner.visibility = View.GONE
                searchContainer.visibility = View.VISIBLE
            }
        }
//--------------------------------------------------------------------------------------
        inputEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (inputEditText.hasFocus() && p0?.isEmpty() == true && historyTrackList.isNotEmpty()) {
                    historyTrackList = searchHistory.getHistoryTrackList()
                    historyAdapter.tracks = historyTrackList
                    historyAdapter.notifyDataSetChanged()
                    historyConteiner.visibility = View.VISIBLE
                    searchContainer.visibility = View.GONE

                } else {
                    historyConteiner.visibility = View.GONE
                    searchContainer.visibility = View.VISIBLE
                }
                searchDebounce()
           }

            override fun afterTextChanged(p0: Editable?) {
            }

        })





        refreshButton.setOnClickListener {
            searchRequest()
        }


        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        clearButton.setOnClickListener {
            inputEditText.setText("")
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            trackList.clear()
            adapter.notifyDataSetChanged()
        }

        val searchArrowBack = findViewById<ImageView>(R.id.searchArrowBack)
        searchArrowBack.setOnClickListener {
            this.finish()
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
                inputText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                //empty
            }
        }
        inputEditText.addTextChangedListener(searchTextWatcher)


    }




    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

   //-----------------------------------------Поиск треков-----------------------------------------------------
    private fun searchRequest() {


       progressBar.visibility = View.VISIBLE

       val consumer = object: TracksInteractor.TracksConsumer {
           override fun consume(foundTracks: domainTracksResponse) {
               handlerSearchTracks.post {
                   progressBar.visibility = View.GONE
                   trackList.clear()
                   trackList.addAll(foundTracks.results)
                   adapter.notifyDataSetChanged()
                   if (foundTracks.resultCode == 200) {
                       if (trackList.isEmpty()) {
                           showMessage(getString(R.string.nothing_found), true)
                       } else {
                           showMessage("", true)
                       }
                   } else {
                       showMessage(getString(R.string.something_went_wrong), false)
                   }
               }
           }
       }

       tracksInteractor.searchTracks(inputEditText.text.toString(), consumer)

       /*iTunesService.findTrack(inputEditText.text.toString()).enqueue(object :
            Callback<TracksResponse> {
            override fun onResponse(call: Call<TracksResponse>,
                                    response: Response<TracksResponse>
            ) {
                progressBar.visibility = View.GONE
                if (response.code() == 200) {
                    trackList.clear()
                    if (response.body()?.results?.isNotEmpty() == true) {
                        trackList.addAll(response.body()?.results!!)
                        adapter.notifyDataSetChanged()
                    }
                    if (trackList.isEmpty()) {
                        showMessage(getString(R.string.nothing_found), true)
                    } else {
                        showMessage("", true)
                    }
                } else {
                    showMessage(getString(R.string.something_went_wrong), false)
                }
            }

            override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                showMessage(getString(R.string.something_went_wrong), false)
            }
        })*/

    }


    private fun showMessage(text: String, isNotFoundTrack: Boolean) {
        if (text.isNotEmpty()) {
            if (isNotFoundTrack) {
                placeholderNotFound.visibility = View.VISIBLE
                placeholderConnection.visibility = View.GONE
                refreshButton.visibility = View.GONE
            } else {
                placeholderNotFound.visibility = View.GONE
                placeholderConnection.visibility = View.VISIBLE
                refreshButton.visibility = View.VISIBLE
            }
            placeholderText.visibility = View.VISIBLE
            placeholderText.text = text
        } else {
            placeholderNotFound.visibility = View.GONE
            placeholderConnection.visibility = View.GONE
            refreshButton.visibility = View.GONE
            placeholderText.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INPUT_TEXT, inputText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        super.onRestoreInstanceState(savedInstanceState)
        inputText = savedInstanceState.getString(INPUT_TEXT, TEXT_DEF)
        inputEditText.setText(inputText)

    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}