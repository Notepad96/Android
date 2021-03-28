package com.example.notificationlayout

import android.Manifest
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

    }

    fun popTracks() {
        tracks = mutableListOf()

        tracks.add(Track("Track 1", "Artist 1", R.drawable.ic_baseline_close_24))
    }

}