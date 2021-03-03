package com.example.customcalendar

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MyAdapter() :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    var calendar: Calendar = Calendar.getInstance()

    var dates = MutableList<MyDate>(DAYS_OF_WEEK * ROW) { MyDate() }
    var prevDays = 0
    var currentDays = 0
    var nextDays = 0
    var selectDays = -1

    companion object {
        const val DAYS_OF_WEEK = 7
        const val ROW = 6
    }
    init {
        getMonthDays()
        selectDays = prevDays + Date().day - 1
    }

    class MyViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        val width = parent.width / 7
        val height = parent.height / ROW

        layout.layoutParams = RecyclerView.LayoutParams(width, height)
        getMonthDays()

        //var day = SimpleDateFormat("yyyy-MM-dd / HH:mm", Locale.getDefault()).format(Date())

        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.layout.day.text = dates[position].day.toString()

        if(position == selectDays) {
            holder.layout.dayBox.setBackgroundResource(R.drawable.border_selected)
        } else {
            holder.layout.dayBox.setBackgroundResource(0)
        }

        holder.layout.dayBox.setOnClickListener {
            selectDays = position
            notifyDataSetChanged()
        }
        holder.layout.dayBox.setOnLongClickListener {

            true
        }

        when(position % DAYS_OF_WEEK) {
            0 -> holder.layout.day.setTextColor(Color.parseColor("#f53a25"))
            6 -> holder.layout.day.setTextColor(Color.parseColor("#2869f7"))
            else -> holder.layout.day.setTextColor(Color.parseColor("#585859"))
        }

        if(position < prevDays || position > currentDays + prevDays - 1) {
            holder.layout.day.alpha = 0.4f
        } else {
            holder.layout.day.alpha = 1.0f
        }

    }

    override fun getItemCount() = dates.size


    fun prevMonth() {
        if(calendar.get(Calendar.MONTH) == 0) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1)
            calendar.set(Calendar.MONTH, 11)
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        }
        getMonthDays()
    }
    fun nextMonth() {
        if(calendar.get(Calendar.MONTH) == 11) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
            calendar.set(Calendar.MONTH, 0)
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
        }
        getMonthDays()
    }

    private fun getMonthDays() {
        var index = 0
        calendar.set(Calendar.DATE, 1)

        prevDays = calendar.get(Calendar.DAY_OF_WEEK) - 1
        currentDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        nextDays = DAYS_OF_WEEK * ROW - (prevDays + currentDays)

        var tempYear = calendar.get(Calendar.YEAR)
        var tempMonth = calendar.get(Calendar.MONTH)
        var prevYear = tempYear
        var prevMonth = tempMonth
        var nextYear = tempYear
        var nextMonth = tempMonth

        var prevCalendar = calendar.clone() as Calendar
        if (prevCalendar.get(Calendar.MONTH) == 0) {
            prevYear--
            prevMonth = 11
            prevCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1)
            prevCalendar.set(Calendar.MONTH, Calendar.DECEMBER)
        } else if(prevCalendar.get(Calendar.MONTH) == 11) {
            nextYear++
            nextMonth = 0
            prevCalendar.set(Calendar.MONTH, prevCalendar.get(Calendar.MONTH) - 1)
        } else {
            prevCalendar.set(Calendar.MONTH, prevCalendar.get(Calendar.MONTH) - 1)
        }

        var prevLast = prevCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - prevDays
        for( i in 1..prevDays) dates[index++].reset(prevYear, prevMonth , ++prevLast, index % 7)
        for( i in 1..currentDays) dates[index++].reset(tempYear, tempMonth, i, index % 7)
        for( i in 1..nextDays) dates[index++].reset(nextYear, nextMonth, i, index % 7)
    }

}