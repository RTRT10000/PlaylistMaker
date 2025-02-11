package com.practicum.playlistmaker.sharing.data.impl

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker.sharing.domain.EmailData

class ExternalNavigator(var application: Application) {

    fun shareLink(shareLink: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareLink)
        val chooserIntent: Intent = Intent.createChooser(shareIntent,"Share with")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(chooserIntent)
    }

    fun openLink(openLink: String) {
        val agreementIntent = Intent(Intent.ACTION_VIEW)
        agreementIntent.data = Uri.parse(openLink)
        agreementIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(agreementIntent)
    }

    fun openEmail(emailData: EmailData) {
        val writeSupportIntent = Intent(Intent.ACTION_SENDTO)
        writeSupportIntent.data = Uri.parse("mailto:")
        writeSupportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailData.mail))
        writeSupportIntent.putExtra(Intent.EXTRA_SUBJECT, emailData.subject)
        writeSupportIntent.putExtra(Intent.EXTRA_TEXT, emailData.message)
        writeSupportIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(writeSupportIntent)
    }

}


















