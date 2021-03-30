package com.example.notificationlayout

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CreateNotification {

    lateinit var notification: Notification

    fun createNotification(context: Context, track: Track, playBtn: Int, pos: Int, size: Int) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationManager = NotificationManagerCompat.from(context)
//            var mediaSessionCompat = MediaSessionCompat(context, "tag")

            var icon = BitmapFactory.decodeResource(context.resources, track.image)

            notification = NotificationCompat.Builder(context, Companion.CHANNER)
                    .setSmallIcon(R.drawable.ic_baseline_play_arrow_24)
                    .setContentTitle(track.title)
                    .setContentText(track.artist)
                    .setLargeIcon(icon)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build()

            notificationManager.notify(1, notification)
        }
    }

    companion object {
        const val CHANNER = "music"
        const val PREV = "playPrev"
        const val ACTION = "playTemp"
        const val NEXT = "playNext"
    }

}