package com.example.calendarview

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showCal(view: View) {
        val cal = Calendar.getInstance()
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                datePicker, y, m, d-> calText.text = "${y}/${m+1}/${d}"
            Toast.makeText(this, "$y-${m+1}-$d", Toast.LENGTH_SHORT).show()
        },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show()
    }

    fun showTime(view: View) {
        val cal = Calendar.getInstance()
        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {
                timePicker, h, m -> timeText.text = "${h}시 ${m}분"
            Toast.makeText(this, "$h:$m", Toast.LENGTH_SHORT).show()
        },
            cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
    }
}