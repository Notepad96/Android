package com.example.backbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var preTime: Long = 0
    override fun onBackPressed() {
        val temp = System.currentTimeMillis()
        if(temp - preTime >= 3500L) {
            preTime = temp

            // LENGTH_SHORT = 2000 = 2초
            //Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            // LENGTH_LONG = 3500 = 3.5초
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()
        }
    }

}