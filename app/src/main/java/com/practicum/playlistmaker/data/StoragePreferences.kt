package com.practicum.playlistmaker.data

interface StoragePreferences {

    fun getStringFromStorage(): String
    fun putStringToStorage(json: String)
    fun clearStorage()
    fun getBooleanFromStorage(): Boolean
    fun putBooleanToStorage(value: Boolean)
}