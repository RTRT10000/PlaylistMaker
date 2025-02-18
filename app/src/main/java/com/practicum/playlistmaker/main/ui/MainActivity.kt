package com.practicum.playlistmaker.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practicum.playlistmaker.App
import com.practicum.playlistmaker.media.ui.MediaActivity
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.ui.activity.SearchActivity
import com.practicum.playlistmaker.settings.ui.activity.SettingsActivity

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




