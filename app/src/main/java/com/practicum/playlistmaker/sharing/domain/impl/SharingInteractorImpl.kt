package com.practicum.playlistmaker.sharing.domain.impl

import android.content.Context
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.sharing.domain.EmailData
import com.practicum.playlistmaker.sharing.data.impl.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.api.SharingInteractor

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
) : SharingInteractor {

    override fun shareApp(shareAppLink: String) {
        externalNavigator.shareLink(shareAppLink)
    }

    override fun openTerms(termLink: String) {
        externalNavigator.openLink(termLink)
    }

    override fun openSupport(eMailData: EmailData) {
        externalNavigator.openEmail(eMailData)
    }

}





