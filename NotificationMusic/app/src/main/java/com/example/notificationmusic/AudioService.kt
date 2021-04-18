package com.example.notificationmusic

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.provider.MediaStore

class AudioService : Service() {
    var binder = AudioServiceBinder()
    var mediaPlayer: MediaPlayer? = null
    var isPrepared: Boolean = true

    var datas = mutableListOf<Music>()
    var currentPosition = 0

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
        mediaPlayer?.setOnErrorListener() { mp, what, extra ->
            isPrepared = false
            false
        }
        mediaPlayer?.setOnSeekCompleteListener {

        }
        getAudioList()
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

    private fun getAudioList() {
        val projection = arrayOf(
                MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION)
        val selection = null //"${MediaStore.Audio.Media.IS_MUSIC} = 1"
        val selectionArgs = null
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        applicationContext.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                datas.add(Music(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getLong(4) ))
            }
        }
    }

    fun prepare() {
        mediaPlayer?.setDataSource(datas[currentPosition].title)
        mediaPlayer?.prepareAsync()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
    }

    fun play(position: Int) {
        stop()
        prepare()
    }

    fun play() {
        if(isPrepared) {
            mediaPlayer?.start()
        }
    }

    fun pause() {
        if(isPrepared) {
            mediaPlayer?.pause()
        }
    }

    fun forward() {
        if(datas.size - 1 > currentPosition++) {
            currentPosition = 0
        }
        play(currentPosition)
    }

    fun rewind() {
        if(--currentPosition < 0) {
            currentPosition = datas.size - 1
        }
        play(currentPosition)
    }
}