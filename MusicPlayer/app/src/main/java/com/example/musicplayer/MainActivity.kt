package com.example.musicplayer

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.load
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val PERMISSION_CODE = 111
    private val requestPermissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val artUri = Uri.parse("content://media/external/audio/albumart")

    var datas: MutableList<Music> = mutableListOf()
    var mediaPlayer: MediaPlayer? = null
    var position = 0
    var end = 0
    var isPlaying = false

    lateinit var runnable: Runnable
    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
        getAudioList()

        btnPlay.setOnClickListener {
            if(mediaPlayer == null) setMusic()
            isPlaying = !isPlaying
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

    //    권한 체크
    private fun checkPermissions() {
        var rejectedPermissionList = ArrayList<String>()

        for(permission in requestPermissions) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                rejectedPermissionList.add(permission)
            }
        }

        if(rejectedPermissionList.isNotEmpty()) {
            val arr = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(arr), PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()) {
                    for ((i, permission) in permissions.withIndex()) {

                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            Log.d("Msg", "$permission Denied")
                            Toast.makeText(this, "권한을 허용 해주세요. 권한을 묻는 창이 뜨지 않을 경우 설정>애플리케이션에서 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                            checkPermissions()
                        }
                    }
                }
            }
        }
    }

    private fun getAudioList() {
        val projection = arrayOf(
                MediaStore.Files.FileColumns._ID,
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
                datas.add(Music(cursor.getString(0), cursor.getLong(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getLong(5) ))
            }
        }
        end = datas.size
    }

}