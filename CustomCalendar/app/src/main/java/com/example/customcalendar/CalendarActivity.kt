package com.example.customcalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_calendar.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        viewManager = GridLayoutManager(this, 7) // 열 개수(Column Count)
        viewAdapter = MyAdapter(this)

        recyclerView = calendarView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        currentMonth(Calendar.getInstance())
    }
    fun currentMonth(calendar: Calendar) {
        tempDay.text = SimpleDateFormat("yyyy MM").format(calendar.time)
    }

    fun prev(view: View) {
        viewAdapter.prevMonth()
        currentMonth(viewAdapter.calendar)
        viewAdapter.notifyDataSetChanged()
    }
    fun next(view: View) {
        viewAdapter.nextMonth()
        currentMonth(viewAdapter.calendar)
        viewAdapter.notifyDataSetChanged()
    }
}