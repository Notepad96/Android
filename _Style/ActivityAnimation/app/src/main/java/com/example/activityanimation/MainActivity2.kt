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
        // 1.
//        overridePendingTransition(R.anim.up_from, R.anim.up_to)

        // 2.
//         overridePendingTransition(R.anim.down_from, R.anim.down_to)

        // 3.
//        overridePendingTransition(R.anim.right_from, R.anim.right_to)

        // 4.
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
    }
}