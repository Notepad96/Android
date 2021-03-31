package com.example.notificationlayout

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CreateNotification {

    companion object {
        const val CHANNER = "music"
        const val PREV = "playPrev"
        const val ACTION = "playTemp"
        const val NEXT = "playNext"

        lateinit var notification: Notification

        fun createNotification(context: Context, track: Track, playBtn: Int, pos: Int, size: Int) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var notificationManager = NotificationManagerCompat.from(context)
//            var mediaSessionCompat = MediaSessionCompat(context, "tag")

                var icon = BitmapFactory.decodeResource(context.resources, track.image)

                // Prev
                var pendingIntentPrevious: PendingIntent?
                var drw_prev: Int
                if(pos == 0) {
                    pendingIntentPrevious = null
                    drw_prev = 0
                } else {
                    var intentPrevious = Intent(context, NotificationActionService::class.java)
                            .setAction(PREV)
                    pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                    intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT)
                    drw_prev = R.drawable.ic_baseline_fast_rewind_24
                }

                // Temp
                var intentPlay = Intent(context, NotificationActionService::class.java)
                        .setAction(ACTION)
                var pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                        intentPlay, PendingIntent.FLAG_UPDATE_CURRENT)


                // Next
                var pendingIntentNext: PendingIntent?
                var drw_next: Int
                if(pos == 0) {
                    pendingIntentNext = null
                    drw_next = 0
                } else {
                    var intentNext = Intent(context, NotificationActionService::class.java)
                            .setAction(NEXT)
                    pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                            intentNext, PendingIntent.FLAG_UPDATE_CURRENT)
                    drw_next = R.drawable.ic_baseline_fast_forward_24
                }

                notification = NotificationCompat.Builder(context, Companion.CHANNER)
                        .setSmallIcon(R.drawable.ic_baseline_play_arrow_24)
                        .setContentTitle(track.title)
                        .setContentText(track.artist)
                        .setLargeIcon(icon)
                        .setOnlyAlertOnce(true)
                        .setShowWhen(false)
                        .addAction(drw_prev, "Previous", pendingIntentPrevious)
                        .addAction(playBtn, "Play", pendingIntentPlay)
                        .addAction(drw_next, "Next", pendingIntentNext)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build()

                notificationManager.notify(1, notification)
            }
        }
    }

}