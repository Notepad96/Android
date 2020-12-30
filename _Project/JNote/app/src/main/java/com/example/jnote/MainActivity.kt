package com.example.jnote

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.jnote.DB.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            if(db?.hanjaDao()?.getCount() == 0L ) {
                createDatabase()
            }
        }

    }

    override fun onDestroy() {
        db = null
        AppDataBase.deleteInstance()
        super.onDestroy()
    }

    fun createDatabase() {
        val inputStream = resources.assets.open("hanja.txt")
        inputStream.bufferedReader().readLines().forEach {
            var line = it.trim().split("\t").map { it.trim { it <= '\"' } }
            Log.d("test", line.joinToString("|"))

            CoroutineScope(Dispatchers.IO).launch {
                db?.hanjaDao()?.insertHanja(line[0].toInt(), line[1], line[2], line[3])
            }
        }
    }

}