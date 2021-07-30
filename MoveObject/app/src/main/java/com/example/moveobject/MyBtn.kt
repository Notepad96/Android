package com.example.moveobject

import android.app.ActionBar
import android.content.Context
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Button

class MyBtn(context: Context) {
    private var btn = Button(context)
    var w = 200
    var h = 100
    var txt = "버튼"

    var x = 0
    var y = 0

    init {
        btn.width = w
        btn.height = h
        btn.text = txt
        btn.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                x = event.x.toInt()
                y = event.y.toInt()
            } else if(event.action == MotionEvent.ACTION_MOVE) {

                v.layoutParams = (v.width, v.height, (event.x - (v.width / 2)).toInt(), (event.y - v.height).toInt())
            }
            true
        }
    }

    fun getButton(): Button {
        return btn
    }
}