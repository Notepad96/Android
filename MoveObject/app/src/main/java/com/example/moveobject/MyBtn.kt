package com.example.moveobject

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.View
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
        startMove()
        btn.setOnClickListener {
            val intent = Intent(context, SettingBtn::class.java)
            context.startActivity(intent)
        }
    }

    fun startMove() {
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

    fun fixedMove() {
        btn.setOnTouchListener { v, event ->
            true
        }
    }

    fun getButton(): Button {
        return btn
    }
}