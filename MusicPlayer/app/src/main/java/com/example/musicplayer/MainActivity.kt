package com.example.musicplayer

import android.Manifest
import android.content.ContentUris
import android.content.SharedPreferences
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_music_detail.*

class MainActivity : AppCompatActivity() {
    private val PERMISSION_CODE = 111
    private val requestPermissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private val artUri: Uri = Uri.parse("content://media/external/audio/albumart")
    private lateinit var musics: MusicList

    lateinit var musicPreferences: SharedPreferences

    lateinit var runnable: Runnable
    var handler = Handler()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
        musicPreferences = getSharedPreferences("music", MODE_PRIVATE)

        musics = MusicList(applicationContext)
        musics.initMusicList()

        viewManager = LinearLayoutManager(applicationContext)
        viewAdapter = MyAdapter(musics.musicList)

        recyclerView = listMusic.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        var pos = musicPreferences.getInt("pos", 0)
        imgTempPlay.load( ContentUris.withAppendedId(artUri , musics.musicList[pos].album_id)) {
            crossfade(true)
            error(R.drawable.empty)
        }
        txtTempPlay.text = "${musics.musicList[pos].artist} - ${musics.musicList[pos].title}"
        if(musicPreferences.getBoolean("playing", false)) {
            btnTempPlay.setBackgroundResource(R.drawable.ic_baseline_pause_circle_vector)
        } else {
            btnTempPlay.setBackgroundResource(R.drawable.ic_baseline_play_circle_vector)
        }
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


}