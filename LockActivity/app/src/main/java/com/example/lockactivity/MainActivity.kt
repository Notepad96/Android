package com.example.lockactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var count = 0
    var password = ""
    var viewText = arrayListOf<String>("○", "○", "○", "○", "○", "○")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lockPwd.text = viewText.joinToString("")
    }

    fun numInput(view: View) {
        when(view.id) {
            R.id.lockDelete -> {
                viewText[--count] = "○"
            }
            R.id.lockReset -> {
                count = 0
                viewText = arrayListOf<String>("○", "○", "○", "○", "○", "○")
            }
            else -> {
                ++count
            }
        }
        for(i in 0 until count) viewText[i] = "●"

        lockPwd.text = viewText.joinToString("")
    }

}