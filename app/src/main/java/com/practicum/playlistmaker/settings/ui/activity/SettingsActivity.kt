package com.practicum.playlistmaker.settings.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


import com.google.android.material.switchmaterial.SwitchMaterial

import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.settings.ui.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        viewModel = ViewModelProvider(this, SettingsViewModel.getViewModelFactory())[SettingsViewModel::class.java]

        //val settingsInteractor = Creator.getSettingInteractor()
        //themeSwitcher.setChecked((applicationContext as App).darkTheme)

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
           viewModel.openSupport()
        }

        val tvAgreement = findViewById<TextView>(R.id.tvAgreement)
        tvAgreement.setOnClickListener {
            viewModel.openTerms()
        }

        val tvShare = findViewById<TextView>(R.id.tvShare)
        tvShare.setOnClickListener {
          viewModel.shareApp()
        }


    }
}