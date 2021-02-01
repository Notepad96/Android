package com.example.activityanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.up_from, R.anim.up_to)
    }
}