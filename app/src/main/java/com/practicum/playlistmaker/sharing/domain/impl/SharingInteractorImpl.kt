package com.practicum.playlistmaker.sharing.domain.impl

import android.content.Context
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.sharing.data.EmailData
import com.practicum.playlistmaker.sharing.data.impl.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.api.SharingInteractor

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
    private val context: Context
) : SharingInteractor {

    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
       return context.getString(R.string.settings_share)
    }

    private fun getTermsLink(): String {
        return context.getString(R.string.settings_agreement)
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData(
            message = context.getString(R.string.settings_mail_message),
            subject = context.getString(R.string.settings_mail_subject),
            mail = context.getString(R.string.settings_mail)
        )
    }

}





