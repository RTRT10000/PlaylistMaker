package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val tvWriteSupport = findViewById<TextView>(R.id.tvWriteSupport)
        tvWriteSupport.setOnClickListener {
            val message = "Спасибо разработчикам и разработчицам за крутое приложение!"
            val subject = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
            val writeSupportIntent = Intent(Intent.ACTION_SENDTO)
            writeSupportIntent.data = Uri.parse("mailto:")
            writeSupportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("rt10000@ya.ru"))
            writeSupportIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            writeSupportIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(writeSupportIntent)

            Toast.makeText(this@SettingsActivity, "Здесь какой-то текст", Toast.LENGTH_SHORT).show()
        }

        val tvAgreement = findViewById<TextView>(R.id.tvAgreement)
        tvAgreement.setOnClickListener {
            val  url="https://yandex.ru/legal/practicum_offer/"
            val agreementIntent = Intent(Intent.ACTION_VIEW)
            agreementIntent.data = Uri.parse(url)
            startActivity(agreementIntent)
        }

        val tvShare = findViewById<TextView>(R.id.tvShare)
        tvShare.setOnClickListener {
          val shareIntent = Intent(Intent.ACTION_SEND)
          shareIntent.type = "text/plain"
          shareIntent.putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/?utm_source=yandex-desktop-all&utm_medium=regular&utm_campaign=ya-ru_popup-all-services-desktop")
          startActivity(Intent.createChooser(shareIntent,"Share with"))
        }
    }
}