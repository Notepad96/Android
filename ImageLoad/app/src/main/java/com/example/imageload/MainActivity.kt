package com.example.imageload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        imgView.load("https://cdn.pixabay.com/photo/2013/08/20/15/47/poppies-174276__180.jpg")

//        imgView.load(R.drawable.image_back)

        imgView.load(File("image0.jpg"))
    }
}