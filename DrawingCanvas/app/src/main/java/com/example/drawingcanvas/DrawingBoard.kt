package com.example.drawingcanvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View

class DrawingBoard(context: Context) : View(context) {
    var paint = Paint()
    var path = Path()
    private var xx = 0F
    private var yy = 0F

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        xx = event!!.x
        yy = event!!.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(xx, yy)
            }
            MotionEvent.ACTION_MOVE -> {
                xx = event!!.x
                yy = event!!.y

                path.lineTo(xx, yy)
            }
        }
        invalidate()
        return true
    }

    fun colorCha(color: String) {

    }

}