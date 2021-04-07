package com.example.notificationmusic

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var LOADER_ID = 1125
    val PERMISSIONCODE = 111

    val requestPermissionms = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var datas: MutableList<MusicInfo> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()

        btnStart.setOnClickListener {
            getAudioList()

            viewManager = LinearLayoutManager(applicationContext)
            viewAdapter = ListAdapter(datas)

            recyclerView = listRecycler.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
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
                        MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA
                    )
                    var select = "${MediaStore.Audio.Media.IS_MUSIC} = 1"
                    var sort = "${MediaStore.Audio.Media.TITLE} ASC"

                    return CursorLoader(applicationContext, uri, musicInfo, select, null, sort)
                }

                override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
                    if (data != null && data.count > 0) {
                        while (data.moveToNext()) {
                            datas.add(
                                MusicInfo(
                                    data.getLong(data.getColumnIndex(MediaStore.Audio.Media._ID)),
                                    data.getLong(data.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
                                    data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                                    data.getString(data.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                                    data.getString(data.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                                    data.getLong(data.getColumnIndex(MediaStore.Audio.Media.DURATION)),
                                    data.getString(data.getColumnIndex(MediaStore.Audio.Media.DATA))
                                )
                            )

//                            Log.d(
//                                "musicList",
//                                "Title ${data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE))}"
//                            )
                        }
                        Toast.makeText(applicationContext, "${datas.size}", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onLoaderReset(loader: Loader<Cursor>) {

                }
            })
    }
}