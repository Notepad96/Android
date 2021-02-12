package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    var time = 0
    var isStart = false
    var isRecord = false
    var timerObj: Timer? = null
    var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn.setOnClickListener {
            isStart = !isStart
            when(isStart) {
                true -> start()
                false -> pause()
            }
        }
        recordBtn.setOnClickListener {
            when(isRecord) {
                true -> record()
                false -> init()
            }
        }
    }

    private fun start() {
        recordBtn.visibility = View.VISIBLE
        startBtn.text = resources.getString(R.string.pause)
        startBtn.setBackgroundResource(R.drawable.btn_stop)

        timerObj = timer(period = 10) {
            time++
            val min = time / 6000
            val sec = (time / 100) % 60
            val milli = time % 100
            runOnUiThread {
                minuteTime.text = if(min < 10) "0$min" else "$min"
                secondTime.text = if(sec < 10) "0$sec" else "$sec"
                milliTime.text = if(milli < 10) "0$milli" else "$milli"
            }
        }
    }

    private fun pause() {
        startBtn.text = resources.getString(R.string.start)
        startBtn.setBackgroundResource(R.drawable.btn_start)

        timerObj?.cancel()
    }

    private fun record() {

    }

    private fun init() {

    }

}