package com.example.jnote

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View

class PaintBoard(context: Context) : View(context) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}