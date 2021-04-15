package com.example.notificationmusic

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager

class AudioService : Service() {
    var binder = AudioServiceBinder()
    var mediaPlayer: MediaPlayer? = null
    var isPrepared: Boolean = true

    class AudioServiceBinder : Binder() {
        fun getService(): AudioService {
            return AudioService()
        }
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer?.setOnPreparedListener {
            isPrepared = true
            mediaPlayer?.start()
        }
        mediaPlayer?.setOnCompletionListener {
            isPrepared = false
        }
        mediaPlayer?.setOnErrorListener {
            isPrepared = false
            false
        }
        mediaPlayer?.setOnSeekCompleteListener {

        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        if(mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}