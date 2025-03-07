package com.practicum.playlistmaker.search.ui.view_model

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.impl.SearchHistoryInteractor
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.practicum.playlistmaker.search.domain.api.OnTrackItemClickListener
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.domain.state.TracksListState


class SearchViewModel(
    private val searchHistoryInteractor: SearchHistoryInteractor,
    private val tracksInteractor: TracksInteractor
) : ViewModel() {

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchRequest(searchString) }

    private var trackListLiveData = MutableLiveData<List<Track>>()
    fun getTrackListLiveData(): LiveData<List<Track>> = trackListLiveData

    private var historyTrackListLiveData = MutableLiveData(searchHistoryInteractor.getHistoryTrackList())
    fun getHistoryTrackListLiveData(): LiveData<ArrayList<Track>> = historyTrackListLiveData

    private var stateLiveData = MutableLiveData<TracksListState>()
    fun getStateLiveData(): LiveData<TracksListState> = stateLiveData

    private var searchString: String = ""

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    fun searchDebounce(searchString: String) {
        this.searchString = searchString
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun removeSearchDebounce() {
        handler.removeCallbacks(searchRunnable)
    }


    fun setHistoryTrackList() {
        stateLiveData.postValue(TracksListState.HistoryTrackList)
    }

    fun setInput(isInputNullOrEmpty: Boolean) {
        stateLiveData.postValue(TracksListState.Input(isInputNullOrEmpty))
    }

    fun getOnTrackItemClickListener(): OnTrackItemClickListener {
        return searchHistoryInteractor
    }



    fun clearHistotyTrackList(isInputNullOrEmpty: Boolean) {
        searchHistoryInteractor.historyTracksListRepository.clearHistoryTrackList()
        historyTrackListLiveData.postValue(searchHistoryInteractor.getHistoryTrackList())
        stateLiveData.postValue(TracksListState.Input(isInputNullOrEmpty))
    }

    fun searchRequest(searchString: String) {

        stateLiveData.postValue(TracksListState.Loading)

        val consumer = object : TracksInteractor.TracksConsumer {
            override fun consume(foundTracks: List<Track>?, errorMessage: String?) {
                if (foundTracks != null) {
                    trackListLiveData.postValue(foundTracks!!)
                    stateLiveData.postValue(TracksListState.ContentTracks)
                } else  if (errorMessage == "Tracks Not Found") {
                    trackListLiveData.postValue(ArrayList<Track>())
                    stateLiveData.postValue(TracksListState.EmptyContentTracks)
                } else {
                    trackListLiveData.postValue(ArrayList<Track>())
                    stateLiveData.postValue(TracksListState.Error(errorMessage))
                }
            }
        }

        tracksInteractor.searchTracks(searchString, consumer)

    }





}




