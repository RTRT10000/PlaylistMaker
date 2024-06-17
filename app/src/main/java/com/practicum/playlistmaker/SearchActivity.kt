package com.practicum.playlistmaker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class SearchActivity : AppCompatActivity() {


    private val iTunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ItunesApi::class.java)

    private lateinit var recycler: RecyclerView
    private lateinit var refreshButton: Button
    private lateinit var placeholderNotFound: ImageView
    private lateinit var placeholderConnection: ImageView
    private lateinit var inputEditText: EditText
    private lateinit var placeholderText: TextView



    private  val trackList = ArrayList<Track>()

    private val adapter = TrackAdapter()


    companion object {
       const val INPUT_TEXT = "INPUT_TEXT"
       const val TEXT_DEF = ""
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        placeholderNotFound = findViewById(R.id.ivPlaceHolderNotFound)
        placeholderConnection = findViewById(R.id.ivPlaceHolderConnect)
        refreshButton = findViewById(R.id.btnRefresh)
        placeholderText = findViewById(R.id.tvTextPlaceholder)
        recycler = findViewById<RecyclerView>(R.id.rvTrackList)

        adapter.tracks = trackList

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter


        inputEditText = findViewById<EditText>(R.id.inputEditText)
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                iTunesService.findTrack(inputEditText.text.toString()).enqueue(object :  Callback<TracksResponse>  {
                    override fun onResponse(call: Call<TracksResponse>,
                                            response: Response<TracksResponse>) {
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
                        showMessage(getString(R.string.something_went_wrong), false)
                    }
                }



                )
                true
            }
            false
        }

        refreshButton.setOnClickListener {
            iTunesService.findTrack(inputEditText.text.toString()).enqueue(object :  Callback<TracksResponse>  {
                override fun onResponse(call: Call<TracksResponse>,
                                        response: Response<TracksResponse>) {
                    if (response.code() == 200) {
                        trackList.clear()
                        if (response.body()?.results?.isNotEmpty() == true) {
                            trackList.addAll(response.body()?.results!!)
                            adapter.notifyDataSetChanged()
                        }
                        if (trackList.isEmpty()) {
                            showMessage(getString(R.string.nothing_found), true)
                        } else {
                           showMessage("", false)
                        }
                    } else {
                        showMessage(getString(R.string.something_went_wrong), false)
                    }
                }

                override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                    showMessage(getString(R.string.something_went_wrong), false)
                }
            })
        }


        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        clearButton.setOnClickListener {
            inputEditText.setText("")
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            trackList.clear()
            adapter.notifyDataSetChanged()
        }

        val searchArrowBack = findViewById<ImageView>(R.id.searchArrowBack)
        searchArrowBack.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
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

        val sharedPref = getSharedPreferences(PLAYLIST_PREFERENCES, MODE_PRIVATE)
        val searchHistory = SearchHistory(sharedPref)
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
            trackList.clear()
            adapter.notifyDataSetChanged()
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

private var inputText: String = ""