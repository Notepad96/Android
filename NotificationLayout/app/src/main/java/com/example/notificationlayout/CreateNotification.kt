package com.example.notificationlayout

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationManagerCompat

class CreateNotification {
    private val CHANNER = "music"
    private val PREV = "playPrev"
    private val ACTION = "playTemp"
    private val NEXT = "playNext"

    var notification: Notification = Notification();

    fun createNotification(context: Context, track: Track, playBtn: Int, pos: Int, size: Int) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationManager = NotificationManagerCompat.from(context)
            var mediaSessionCompat = MediaSessionCompat(context, "tag")

            var bitmap = BitmapFactory.decodeResource(context.resources, track.image)


        }
    }

}