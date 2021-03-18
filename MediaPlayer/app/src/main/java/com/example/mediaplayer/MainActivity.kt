package com.example.mediaplayer

import android.content.res.AssetManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun start(view: View) {
        val mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        mediaPlayer.start()

        Toast.makeText(view.context, "Music Start", Toast.LENGTH_SHORT).show()
    }
}