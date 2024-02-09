package com.practicum.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnFind = findViewById<Button>(R.id.btnFind)

//        val buttonFindListener:  View.OnClickListener = object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                Toast.makeText(this@MainActivity,"Нажата кнопка поиск", Toast.LENGTH_SHORT).show()
//            }
//        }
        btnFind.setOnClickListener {
            val findIntent = Intent(this, FindActivity::class.java)
            startActivity(findIntent)
        }

       val btnMedia =  findViewById<Button>(R.id.btnMedia)
       //btnMedia.setOnClickListener {Toast.makeText(this@MainActivity,"Нажата кнопка медиатека", Toast.LENGTH_SHORT).show()}
        btnMedia.setOnClickListener {
            val mediaIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaIntent)
        }

       val btnSetting = findViewById<Button>(R.id.btnSetting)
       btnSetting.setOnClickListener {
           val settingIntent = Intent(this, SettingsActivity::class.java)
           startActivity(settingIntent)
       }
    }
}