package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    var time = 0
    var isStart = false
    var isRecord = false
    var timerObj: Timer? = null
    var lap = 1

    var timeData: ArrayList<TimeRecord> = arrayListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

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
                false -> reset()
            }
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(timeData)
        recyclerView = timeList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun start() {
        recordBtn.visibility = View.VISIBLE
        startBtn.text = resources.getString(R.string.pause)
        startBtn.setBackgroundResource(R.drawable.btn_stop)
        isRecord = true
        recordBtn.text = resources.getString(R.string.record)

        timerObj = timer(period = 10) {
            time++
            val min = getMin()
            val sec = getSec()
            val milli = getMilli()
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
        isRecord = false
        recordBtn.text = resources.getString(R.string.reset)

        timerObj?.cancel()
    }

    private fun record() {
        header.visibility = View.VISIBLE
        val before = if(timeData.isEmpty()) 0 else timeData.last().allTime
        timeData.add(TimeRecord(lap, time - before , time))
        recyclerView.smoothScrollToPosition(timeData.size - 1)
        recyclerView.invalidate()
        lap++
    }

    private fun reset() {
        recordBtn.visibility = View.GONE
        isRecord = false
        timeData?.clear()
        header.visibility = View.GONE

        minuteTime.text = "00"
        secondTime.text = "00"
        milliTime.text = "00"
        time = 0
        lap = 1
        timerObj?.cancel()
    }

    private fun getMin(): Int {
        return time / 6000
    }

    private fun getSec(): Int {
        return (time / 100) % 60
    }

    private fun getMilli(): Int {
        return time % 100
    }

}