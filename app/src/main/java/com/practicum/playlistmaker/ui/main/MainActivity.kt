package com.practicum.playlistmaker.ui.main

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.ui.media.MediaActivity
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.search.SearchActivity
import com.practicum.playlistmaker.ui.settings.SettingsActivity

const val PLAYLIST_PREFERENCES = "playlist_preferences"
const val DARK_THEME = "dark_theme"
const val SEARCH_HISTORY_LIST = "search_history_list"





class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnFind = findViewById<Button>(R.id.btnFind)

        btnFind.setOnClickListener {
            val findIntent = Intent(this, SearchActivity::class.java)
            startActivity(findIntent)
        }

       val btnMedia =  findViewById<Button>(R.id.btnMedia)

        btnMedia.setOnClickListener {
            val mediaIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaIntent)
        }

       val btnSetting = findViewById<Button>(R.id.btnSetting)
       btnSetting.setOnClickListener {
           val settingIntent = Intent(this, SettingsActivity::class.java)
           startActivity(settingIntent)
       }

        (applicationContext as App).switchTheme( (applicationContext as App).darkTheme)

    }
}

class App : Application() {

    var darkTheme = false
    lateinit var  sharedPref: SharedPreferences


    override fun onCreate() {
        super.onCreate()
        Creator.initApplication(this)
       // sharedPref = getSharedPreferences(PLAYLIST_PREFERENCES, Application.MODE_PRIVATE)
        sharedPref = Creator.provideSharedPrefernces()
        darkTheme = sharedPref.getBoolean(DARK_THEME,false)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}


