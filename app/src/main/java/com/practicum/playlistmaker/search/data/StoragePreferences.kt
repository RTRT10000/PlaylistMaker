package com.practicum.playlistmaker.search.data

interface StoragePreferences {

    fun getStringFromStorage(): String
    fun putStringToStorage(json: String)
    fun clearStorage()
}