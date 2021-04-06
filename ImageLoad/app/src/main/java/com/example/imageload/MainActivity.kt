package com.example.imageload

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgView.load("https://cdn.pixabay.com/photo/2013/08/20/15/47/poppies-174276__180.jpg")

//        imgView.load(R.drawable.image_back)


//        var extFile = File(applicationContext.filesDir, "image0.jpg")
//        var path = Environment.getExternalStorageDirectory().path
//        Toast.makeText(applicationContext, "$path/Download/image0.jpg", Toast.LENGTH_SHORT).show()
//        imgView.load(extFile)


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

}