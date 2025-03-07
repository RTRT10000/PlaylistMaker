package com.practicum.playlistmaker.settings.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.switchmaterial.SwitchMaterial

import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.settings.ui.view_model.SettingsViewModel
import com.practicum.playlistmaker.sharing.domain.EmailData
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsActivity : AppCompatActivity() {

    private  val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val themeSwitcher: SwitchMaterial = findViewById(R.id.themeSwitcher)


        viewModel.getThemeSwitcher().observe(this) { checked ->
            themeSwitcher.setChecked(checked)

        }

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            viewModel.switchTheme(checked)
        }

        val tvSettingsArrowBack = findViewById<ImageView>(R.id.tvSettingsArrowBack)
        tvSettingsArrowBack.setOnClickListener {
            this.finish()
        }

        val tvWriteSupport = findViewById<TextView>(R.id.tvWriteSupport)
        tvWriteSupport.setOnClickListener {
            val eMailData: EmailData = EmailData(
                message = getString(R.string.settings_mail_message),
                subject = getString(R.string.settings_mail_subject),
                mail = getString(R.string.settings_mail)
            )
            viewModel.openSupport(eMailData)
        }

        val tvAgreement = findViewById<TextView>(R.id.tvAgreement)
        tvAgreement.setOnClickListener {
            viewModel.openTerms(getString(R.string.settings_agreement))
        }

        val tvShare = findViewById<TextView>(R.id.tvShare)
        tvShare.setOnClickListener {
          viewModel.shareApp(getString(R.string.settings_share))
        }


    }
}