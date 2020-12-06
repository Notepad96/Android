package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val textView = findViewById<TextView>(R.id.text2)
        textView.text = "${textView.text.toString()}\n${intent.getStringExtra("msg")}"
    }

    fun next(v: View) {
        val intent = Intent(this, MainActivity3::class.java)
        startActivity(intent)
    }
}