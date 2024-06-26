package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val tvSettingsArrowBack = findViewById<ImageView>(R.id.tvSettingsArrowBack)
        tvSettingsArrowBack.setOnClickListener {
            this.finish()
        }

        val tvWriteSupport = findViewById<TextView>(R.id.tvWriteSupport)
        tvWriteSupport.setOnClickListener {
            val message = getString(R.string.settings_mail_message)
            val subject = getString(R.string.settings_mail_subject)
            val mail = getString(R.string.settings_mail)
            val writeSupportIntent = Intent(Intent.ACTION_SENDTO)
            writeSupportIntent.data = Uri.parse("mailto:")
            writeSupportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
            writeSupportIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            writeSupportIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(writeSupportIntent)
        }

        val tvAgreement = findViewById<TextView>(R.id.tvAgreement)
        tvAgreement.setOnClickListener {
            val  url=getString(R.string.settings_agreement)
            val agreementIntent = Intent(Intent.ACTION_VIEW)
            agreementIntent.data = Uri.parse(url)
            startActivity(agreementIntent)
        }

        val tvShare = findViewById<TextView>(R.id.tvShare)
        tvShare.setOnClickListener {
          val share = getString(R.string.settings_share)
          val shareIntent = Intent(Intent.ACTION_SEND)
          shareIntent.type = "text/plain"
          shareIntent.putExtra(Intent.EXTRA_TEXT, share)
          startActivity(Intent.createChooser(shareIntent,"Share with"))
        }


        val themeSwitcher: SwitchMaterial = findViewById(R.id.themeSwitcher)



        themeSwitcher.setOnCheckedChangeListener  {switcher, checked ->
            (applicationContext as App).darkTheme = checked
            val sharedPrefs = getSharedPreferences(PLAYLIST_PREFERENCES, MODE_PRIVATE)
            sharedPrefs.edit()
                .putBoolean(DARK_THEME,checked)
                .apply()
            (applicationContext as App).switchTheme(checked)
        }

        themeSwitcher.setChecked((applicationContext as App).darkTheme)




    }
}