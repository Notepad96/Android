package com.example.notificationmusic

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var LOADER_ID = 1125
    val PERMISSIONCODE = 111

    val requestPermissionms = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
        btnStart.setOnClickListener {
            getAudioList()
        }
    }

//    권한 체크
    fun checkPermissions() {
        var rejectedPermissionList = ArrayList<String>()

        for(permission in requestPermissionms) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                rejectedPermissionList.add(permission)
            }
        }

        if(rejectedPermissionList.isNotEmpty()) {
            val arr = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(arr), PERMISSIONCODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSIONCODE -> {
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
        LoaderManager.getInstance(this).initLoader(
            LOADER_ID,
            null,
            object : LoaderManager.LoaderCallbacks<Cursor> {
                override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                    var uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

                    var musicInfo = arrayOf(
                        MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA
                    )
                    var select = "${MediaStore.Audio.Media.IS_MUSIC} = 1"
                    var sort = "${MediaStore.Audio.Media.TITLE} ASC"

                    return CursorLoader(applicationContext, uri, musicInfo, select, null, sort)
                }

                override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
                    Toast.makeText(applicationContext, "${data?.count}", Toast.LENGTH_SHORT).show()
                    if (data != null && data.count > 0) {
                        while (data.moveToNext()) {
                            Log.d(
                                "musicList",
                                "Title ${data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE))}"
                            )
//                            Toast.makeText(applicationContext, "Test", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onLoaderReset(loader: Loader<Cursor>) {

                }
            })
    }
}