package com.example.notificationmusic

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AudioService : Service {
    val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}