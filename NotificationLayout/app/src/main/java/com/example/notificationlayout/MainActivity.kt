package com.example.notificationlayout

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager

class MainActivity : AppCompatActivity() {
    lateinit var notiManager: NotificationManager
    lateinit var tracks: MutableList<Track>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popTracks()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    private fun createChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(CreateNotification.CHANNER,
            "Dev", NotificationManager.IMPORTANCE_LOW)
        }
    }

    fun popTracks() {
        tracks = mutableListOf()

        tracks.add(Track("Track 1", "Artist 1", R.drawable.ic_baseline_close_24))
        tracks.add(Track("Track 2", "Artist 2", R.drawable.ic_baseline_close_24))
        tracks.add(Track("Track 3", "Artist 3", R.drawable.ic_baseline_close_24))
        tracks.add(Track("Track 4", "Artist 4", R.drawable.ic_baseline_close_24))
    }

}