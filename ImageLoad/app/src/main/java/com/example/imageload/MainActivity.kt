package com.example.imageload

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.load
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    val requestPermissionms = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()

        imgView.load("https://cdn.pixabay.com/photo/2013/08/20/15/47/poppies-174276__180.jpg")

//        imgView.load(R.drawable.image_back)

//        /storage/self/primary/Download/image0.jpg
//        var extFile = File(applicationContext.filesDir, "image0.jpg")
//        var path = Environment.getExternalStorageDirectory().path
//        Toast.makeText(applicationContext, "$extFile", Toast.LENGTH_SHORT).show()
//        imgView.load(File("/storage/self/primary/Download/image0.jpg"))


        /*
        CoroutineScope(Dispatchers.IO).launch {
            val request = ImageRequest.Builder(applicationContext)
                    .data("https://cdn.pixabay.com/photo/2013/08/20/15/47/poppies-174276__180.jpg")
                    .build()

            val dr = imageLoader.execute(request).drawable
            imgView.load(dr)
        }
        */
    }

    private fun checkPermissions() {
        var rejectedPermissionList = ArrayList<String>()

        for(permission in requestPermissionms) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                rejectedPermissionList.add(permission)
            }
        }

        if(rejectedPermissionList.isNotEmpty()) {
            val arr = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(arr), 111)
        }
    }

}