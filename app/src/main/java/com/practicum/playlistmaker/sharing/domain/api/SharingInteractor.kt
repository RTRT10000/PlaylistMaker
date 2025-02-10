package com.practicum.playlistmaker.sharing.domain.api

import com.practicum.playlistmaker.sharing.domain.EmailData

interface SharingInteractor {
    fun shareApp(shareAppLink: String)
    fun openTerms(termLink: String)
    fun openSupport(eMailData: EmailData)
}