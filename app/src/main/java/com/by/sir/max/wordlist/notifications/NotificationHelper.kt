package com.by.sir.max.wordlist.notifications

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.by.sir.max.wordlist.MainActivity
import com.by.sir.max.wordlist.R

object NotificationHelper {
    const val CHANNEL_ID = "max-sir-Notification-Helper-App-android.by.max.sir.is-soft.com"

    const val NOTIFICATION_ID = 21654653

    fun postNotification(
        context: Context,
        text: String,
        title: String,
        icon: Int = R.drawable.note_task_comment_message_postit_post_it_108544,
        vibrationPattern: Array<Long> = arrayOf(500L, 200L, 500L, 300L, 200L)
    ) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(icon)
            .setVibrate(vibrationPattern.toLongArray())
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }


    fun createNotificationChannel(
        context: Context,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {
        // 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            // 3
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun scheduleNotification(
        context: Context,
        delay: Long = 10000,
        notificationId: Int,
        title: String,
        text: String
    ) { //delay is after how much time(in millis) from current time you want to schedule the notification
        val builder = NotificationCompat.Builder(context)
            .setContentTitle(title)
            .setContentText(text)
            .setVibrate(longArrayOf(50, 65, 165, 894, 654))
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.note_task_comment_message_postit_post_it_108544)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setChannelId("${context.packageName}-${context.getString(R.string.app_name)}")
        val intent = Intent(context, MainActivity::class.java)
        val activity = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        builder.setContentIntent(activity)
        val notification: Notification = builder.build()
        val notificationIntent = Intent(context, MyNotificationPublisher::class.java)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent
    }
}