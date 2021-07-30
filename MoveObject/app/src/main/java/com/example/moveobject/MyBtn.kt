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

    var x = 0f
    var y = 0f



    init {
        btn.width = w
        btn.height = h
        btn.text = txt
        btn.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = v.x - event.rawX
                    y = v.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    v.animate()
                            .x(event.rawX + x)
                            .y(event.rawY + y)
                            .setDuration(0)
                            .start()
                }
            }
            true
        }
    }

    fun getButton(): Button {
        return btn
    }
}