package com.example.customcalendar

import java.util.*

data class MyDate(var year: Int, var month: Int, var day: Int, var yoil: Int) {
    init {
        var cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)
        yoil = cal.get(Calendar.DAY_OF_WEEK)
    }
    override fun toString(): String {
        return "$year-$month-$day-$yoil"
    }
}