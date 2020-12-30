package com.example.txttodb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jnote.AppDataBase

class MainActivity : AppCompatActivity() {
    var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.getInstance(this)
        createDatabase()
    }

    override fun onDestroy() {
        db = null
        AppDataBase.deleteInstance()
        super.onDestroy()
    }

    fun createDatabase() {
        val inputStream = resources.assets.open("hanja.txt")

        inputStream.bufferedReader().readLines().forEach {
            var tok = it.trim().split("\t")
            Log.d("test", tok.toString())
            /*
            CoroutineScope(Dispatchers.IO).launch {
                db?.hanjaDao()?.insertHanja()
            }
             */
        }
    }

}