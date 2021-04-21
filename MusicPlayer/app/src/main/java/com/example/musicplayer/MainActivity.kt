package com.example.musicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val PERMISSION_CODE = 111
    private val requestPermissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var datas: MutableList<Music> = mutableListOf()
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
        getAudioList()

        btnPlay.setOnClickListener {

        }

    }

    //    권한 체크
    fun checkPermissions() {
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

}