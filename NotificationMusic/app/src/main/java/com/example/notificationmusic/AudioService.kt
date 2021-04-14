package com.example.notificationmusic

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class AudioService : Service() {
    var binder = AudioServiceBinder()
    lateinit var mediaPlayer: MediaPlayer
    var isPrepared: Boolean = true

    class AudioServiceBinder : Binder() {
        fun getService(): AudioService {
            return AudioService()
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}