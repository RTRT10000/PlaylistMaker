package com.practicum.playlistmaker.search.data.impl

import android.content.SharedPreferences
import com.practicum.playlistmaker.search.data.StoragePreferences
import com.practicum.playlistmaker.main.ui.SEARCH_HISTORY_LIST

class StoragePreferencesImp(private val sharedPref: SharedPreferences) : StoragePreferences {


    override fun getStringFromStorage(): String {
        return sharedPref.getString(SEARCH_HISTORY_LIST, null) ?: ""
    }

    override fun putStringToStorage(json: String) {
       sharedPref.edit()
           .putString(SEARCH_HISTORY_LIST, json)
           .apply()
    }

    override fun clearStorage() {
        sharedPref.edit()
            .remove(SEARCH_HISTORY_LIST)
            .apply()
    }


}



