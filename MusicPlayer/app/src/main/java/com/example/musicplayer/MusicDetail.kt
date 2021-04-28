package com.example.musicplayer

import android.content.ContentUris
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_music_detail.*

class MusicDetail : AppCompatActivity() {
    var mediaPlayer: MediaPlayer? = null
    var position = 0
    var end = 0
    var isPlaying = false

    lateinit var runnable: Runnable
    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_detail)


        btnPlay.setOnClickListener {
            if(mediaPlayer == null) setMusic()
            isPlaying = !isPlaying
            musicStatus()
        }
        btnRewind.setOnClickListener {
            positionChange(0)
            setMusic()
            musicStatus()
        }
        btnNext.setOnClickListener {
            positionChange(1)
            setMusic()
            musicStatus()
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition
            handler.postDelayed(runnable, 1000)
        }


        mediaPlayer?.setOnSeekCompleteListener {
            positionChange(1)
            setMusic()
            musicStatus()
        }
    }


    private fun musicStatus() {
        if(isPlaying) {
            mediaPlayer?.start()
            btnPlay.setImageResource(R.drawable.ic_baseline_pause_circle_vector)
            handler.postDelayed(runnable, 1000)
        } else {
            mediaPlayer?.pause()
            btnPlay.setImageResource(R.drawable.ic_baseline_play_circle_vector)
        }
    }

    fun positionChange(ac: Int) {
        when(ac) {
            0 -> position--
            1 -> position++
        }
        if(position == end) position = 0
        else if(position < 0) position = end - 1
    }

    private fun setMusic() {
        mediaPlayer?.stop()
        mediaPlayer = null
        val content: Uri = Uri.withAppendedPath(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            datas[position].file_id
        )
        imgMusic.load( ContentUris.withAppendedId(artUri , datas[position].album_id)) {
            crossfade(true)
            error(R.drawable.empty)
        }
        Log.d("uri", content.toString())

        mediaPlayer = MediaPlayer.create(applicationContext, content)

        seekBar.progress = 0
        seekBar.max = mediaPlayer!!.duration
        textTitle.text = "${datas[position].artist} - ${datas[position].title}"
    }
}