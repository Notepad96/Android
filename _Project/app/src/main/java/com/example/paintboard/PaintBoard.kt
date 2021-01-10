package com.example.paintboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.MotionEvent
import android.view.View

data class MyPoint(val x: Float = 0F, val y: Float = 0F,
                 val color: Int = Color.BLACK, val width: Float = 10F,
                 val act: Int = 0)

class PaintBoard(context: Context) : View(context) {
    var pathList: MutableList<MyPoint> = mutableListOf<MyPoint>()

    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val START = 0
    val END = 1
    var color: Int = Color.BLACK
    var size: Float = 10F

    init {
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        for(i in pathList.indices) {
            if(pathList[i].act == END) {
                paint.color = pathList[i].color
                paint.strokeWidth = pathList[i].width

                canvas?.drawLine(pathList[i-1].x, pathList[i-1].y,
                pathList[i].x, pathList[i].y, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                pathList.add(MyPoint(event.x, event.y, color, size, 0))
            }
            MotionEvent.ACTION_MOVE -> {
                pathList.add(MyPoint(event.x, event.y, color, size, 1))
            }
        }
        invalidate()
        return true
    }
}