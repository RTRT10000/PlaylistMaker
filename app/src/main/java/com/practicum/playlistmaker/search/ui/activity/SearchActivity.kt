package com.practicum.playlistmaker.search.ui.activity

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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.domain.state.TracksListState
import com.practicum.playlistmaker.search.ui.view_model.SearchViewModel


class SearchActivity : AppCompatActivity() {


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
    private lateinit var clearButton: ImageView

    private lateinit var progressBar: ProgressBar


    private  val trackList = ArrayList<Track>()
    private var historyTrackList = ArrayList<Track>()


    private val adapter = TrackAdapter()
    private val historyAdapter = TrackAdapter()



    private lateinit var tracksInteractor: TracksInteractor


    companion object {
       const val INPUT_TEXT = "INPUT_TEXT"
       const val TEXT_DEF = ""
   }



    private var inputText: String = ""

    private lateinit var viewModel: SearchViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this, SearchViewModel.getViewModelFactory())[SearchViewModel::class.java]

        placeholderNotFound = findViewById(R.id.ivPlaceHolderNotFound)
        placeholderConnection = findViewById(R.id.ivPlaceHolderConnect)
        refreshButton = findViewById(R.id.btnRefresh)
        placeholderText = findViewById(R.id.tvTextPlaceholder)
        recycler = findViewById(R.id.rvTrackList)
        historyRecycler = findViewById(R.id.rvHistoryTrackList)
        historyConteiner = findViewById(R.id.llHistoryConteiner)
        searchContainer = findViewById(R.id.flSearch)
        progressBar = findViewById(R.id.progressBar)

        adapter.tracks = trackList
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter.onTrackItemClickListener = viewModel.getOnTrackItemClickListener()
        historyAdapter.onTrackItemClickListener = viewModel.getOnTrackItemClickListener()

        historyAdapter.tracks = historyTrackList



        historyRecycler.adapter = historyAdapter
        historyRecycler.layoutManager = LinearLayoutManager(this)

        viewModel.getHistoryTrackListLiveData().observe(this) {historyTrackList ->
            this.historyTrackList.clear()
            this.historyTrackList.addAll(historyTrackList)
            historyAdapter.notifyDataSetChanged()

        }

        viewModel.getStateLiveData().observe(this) {state ->
            render(state)
        }

        viewModel.getTrackListLiveData().observe(this) {foundTracks ->
            trackList.clear()
            trackList.addAll(foundTracks)
            adapter.notifyDataSetChanged()
        }


        btnClearHistoryList = findViewById(R.id.btnClearHistoryList)

        btnClearHistoryList.setOnClickListener {
            viewModel.clearHistotyTrackList(inputEditText.text.isEmpty())
        }



        inputEditText = findViewById<EditText>(R.id.inputEditText)

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && inputEditText.text.isEmpty() && historyTrackList.isNotEmpty()) {
                viewModel.setHistoryTrackList()

            } else {
                viewModel.setInput(inputEditText.text.isEmpty())
            }
        }


        inputEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                  if (inputEditText.hasFocus() && p0?.isEmpty() == true && historyTrackList.isNotEmpty()) {
                    viewModel.setHistoryTrackList()
                  } else {
                    inputText = p0.toString()
                    viewModel.setInput(p0.isNullOrEmpty())
                  }

                  if (!inputText.isNullOrEmpty()) {
                      viewModel.searchDebounce(inputText)
                  } else {
                      viewModel.removeSearchDebounce()
                  }

           }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        refreshButton.setOnClickListener {
           viewModel.searchRequest(inputText)
        }


        clearButton = findViewById<ImageView>(R.id.clearIcon)
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        clearButton.setOnClickListener {
            trackList.clear()
            adapter.notifyDataSetChanged()
            inputEditText.setText("")
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }

        val searchArrowBack = findViewById<ImageView>(R.id.searchArrowBack)
        searchArrowBack.setOnClickListener {
            this.finish()
        }

    }





    private fun render(state: TracksListState) {
        when (state) {
            is TracksListState.Loading ->  showLoading()
            is TracksListState.Input -> showInput(state.isInputNullOrEmpty)
            is TracksListState.HistoryTrackList -> showHistoryTrackList()
            is TracksListState.Error -> showError(state.data)
            is TracksListState.EmptyContentTracks -> showEmptyContent()
            is TracksListState.ContentTracks ->  showTracks()
        }
    }


    private fun showLoading() {
        progressBar.visibility = View.VISIBLE

    }

    private fun showInput(isInputNullOrEmpty: Boolean) {
        historyConteiner.visibility = View.GONE
        searchContainer.visibility = View.VISIBLE
        clearButton.visibility = if (isInputNullOrEmpty)   View.GONE else View.VISIBLE
    }

    private fun showHistoryTrackList() {
        historyConteiner.visibility = View.VISIBLE
        searchContainer.visibility = View.GONE
        clearButton.visibility = View.GONE
    }

    private fun showError(text: String?) {
        progressBar.visibility = View.GONE
        placeholderNotFound.visibility = View.GONE
        placeholderConnection.visibility = View.VISIBLE
        refreshButton.visibility = View.VISIBLE
        placeholderText.visibility = View.VISIBLE
        trackList.clear()
        adapter.notifyDataSetChanged()
        when (text) {
            "Connection Error" -> placeholderText.text = getString(R.string.something_went_wrong)
            "Server Error" -> placeholderText.text = getString(R.string.server_error)
            else -> placeholderText.text = getString(R.string.something_went_wrong)
        }
    }

    private fun showEmptyContent() {
        progressBar.visibility = View.GONE
        placeholderNotFound.visibility = View.VISIBLE
        placeholderConnection.visibility = View.GONE
        refreshButton.visibility = View.GONE
        placeholderText.visibility = View.VISIBLE
        trackList.clear()
        adapter.notifyDataSetChanged()
        placeholderText.text = getString(R.string.nothing_found)
    }


    private fun showTracks() {
        progressBar.visibility = View.GONE
        placeholderNotFound.visibility = View.GONE
        placeholderConnection.visibility = View.GONE
        refreshButton.visibility = View.GONE
        placeholderText.visibility = View.GONE
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


}


