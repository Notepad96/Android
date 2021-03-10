package com.example.customcalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class DateMomo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_momo)

        var today = intent.getParcelableExtra<MyDate>("today")
        var date = Date(today.year, today.month, today.day)
        Toast.makeText(this, "${date.toString()}", Toast.LENGTH_SHORT).show()
        var text = SimpleDateFormat("yy/MM/dd").format(date)
        supportActionBar!!.title = text
    }
}