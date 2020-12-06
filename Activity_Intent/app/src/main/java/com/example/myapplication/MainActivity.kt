package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun next(v: View) {
        val send = Intent(this, MainActivity2::class.java).apply {
            putExtra("msg", "Hi~")
        }
        send.putExtra("msg2", "Bye")
        startActivity(send)
    }
}