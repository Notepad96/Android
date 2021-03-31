package com.example.notificationlayout

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Playble {
    lateinit var notiManager: NotificationManager
    lateinit var tracks: MutableList<Track>

    var position = 0
    var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popTracks()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            registerReceiver(BcReceiver(), IntentFilter("TRACKS_TRACK"))
            startService(Intent(baseContext, OnClearFromRecentService::class.java))
        }

        imgBtn.setOnClickListener {
            if(isPlaying) {
                onTrackPause()
            } else {
                onTrackPlay()
            }
        }

    }

    private fun createChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(CreateNotification.CHANNER,
            "Dev", NotificationManager.IMPORTANCE_LOW)

            notiManager = getSystemService(NotificationManager::class.java)
            if(notiManager != null) {
                notiManager.createNotificationChannel(channel)
            }
        }
    }

    fun popTracks() {
        tracks = mutableListOf()

        tracks.add(Track("Track 1", "Artist 1", R.drawable.ic_baseline_close_24))
        tracks.add(Track("Track 2", "Artist 2", R.drawable.ic_baseline_close_24))
        tracks.add(Track("Track 3", "Artist 3", R.drawable.ic_baseline_close_24))
        tracks.add(Track("Track 4", "Artist 4", R.drawable.ic_baseline_close_24))
    }



    override fun onTrackPrevious() {
        position--
        CreateNotification.createNotification(this, tracks.get(position),
                R.drawable.ic_baseline_fast_rewind_24, position, tracks.size -1)
        textView.text = tracks.get(position).title
    }

    override fun onTrackPlay() {
        CreateNotification.createNotification(this, tracks.get(position),
                R.drawable.ic_baseline_pause_24, position, tracks.size -1)
        imgBtn.setImageResource(R.drawable.ic_baseline_pause_24)
        textView.text = tracks.get(position).title
        isPlaying = true
    }

    override fun onTrackPause() {
        CreateNotification.createNotification(this, tracks.get(position),
                R.drawable.ic_baseline_fast_rewind_24, position, tracks.size -1)
        imgBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        textView.text = tracks.get(position).title
        isPlaying = false
    }

    override fun onTrackNext() {
        position++
        CreateNotification.createNotification(this, tracks.get(position),
                R.drawable.ic_baseline_fast_forward_24, position, tracks.size -1)
        textView.text = tracks.get(position).title
    }


    inner class BcReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var action = intent?.extras?.getString("actionName")
            when(action) {
                CreateNotification.PREV ->
                    onTrackPrevious()
                CreateNotification.PREV ->
                    if(isPlaying) {
                        onTrackPause()
                    } else {
                        onTrackPlay()
                    }
                CreateNotification.NEXT ->
                    onTrackNext()
            }
        }
    }
    var bcReceiver = BcReceiver()

    override fun onDestroy() {
        super.onDestroy()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notiManager.cancelAll()
        }

        unregisterReceiver(bcReceiver)
    }
}