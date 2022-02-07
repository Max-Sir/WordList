package com.by.sir.max.wordlist

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.by.sir.max.wordlist.notifications.NotificationHelper

class MyApplication : Application() {
    override fun onCreate() {
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "App notification channel."
        )
        super.onCreate()
    }
}