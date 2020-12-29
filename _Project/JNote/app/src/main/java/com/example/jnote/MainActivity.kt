package com.example.jnote

import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    val assetManager: AssetManager = resources.assets
    val inputStream: InputStream = assetManager.open("hanja.txt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputStream.bufferedReader().readLines().forEach {
            var tok = it.split("\t")
        }


    }
}