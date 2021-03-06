package com.example.customcalendar

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyDate(var year: Int =0, var month: Int =0, var day: Int =0, var yoil: Int =0) : Parcelable {
    private val yoil_str = "토일월화수목금"
    fun reset(year: Int, month: Int , day: Int , yoil: Int ) {
        this.year = year
        this.month = month
        this.day = day
        this.yoil = yoil
    }
    override fun toString(): String {
        return "$year-$month-$day-$yoil"
    }
    fun getYoil(): Char {
        return yoil_str[yoil]
    }
}