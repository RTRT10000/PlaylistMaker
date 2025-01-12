package com.practicum.playlistmaker.data.impl

import android.content.SharedPreferences
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.data.StoragePreferences
import com.practicum.playlistmaker.ui.main.DARK_THEME
import com.practicum.playlistmaker.ui.main.SEARCH_HISTORY_LIST

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

    override fun getBooleanFromStorage(): Boolean {
        return sharedPref.getBoolean(DARK_THEME,false)
    }

    override fun putBooleanToStorage(checked: Boolean) {
        sharedPref.edit()
            .putBoolean(DARK_THEME,checked)
            .apply()
    }

}



