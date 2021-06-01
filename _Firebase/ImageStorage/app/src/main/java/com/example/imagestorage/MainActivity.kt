package com.example.imagestorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storage = Firebase.storage("gs://imagestorage") // 입력 후 사용


        var count = 1
        btn1.setOnClickListener {
            val imageRef = storage.reference.child("images//image${count}.jpg")
            imageRef.downloadUrl.addOnSuccessListener {
                imageView.load(it)
            }
            if(++count == 4) count = 1
        }
    }
}