package com.example.activityanimation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun move(view: View) {
        var intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)

        // 1. 아래로 애니메이션
//        overridePendingTransition(R.anim.down_from, R.anim.down_to)

        // 2. 위로 애니메이션
//         overridePendingTransition(R.anim.up_from, R.anim.up_to)

        // 3. 오른쪽 슬라이드
//        overridePendingTransition(R.anim.right_from, R.anim.right_to)

        // 4. 점점 사라지기
         overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
    }
}