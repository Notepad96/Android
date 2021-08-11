package com.example.moveobject

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView


class MyLayout(context: Context) {
    private var layout = LinearLayout(context)
    var lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
    var dm = context.resources.displayMetrics
    var w = dm.widthPixels * 1/2
    var h = w * 16/9

    var x = 0f
    var y = 0f

    init {
        lp.width = w
        lp.height = h
        lp.gravity = Gravity.CENTER
        layout.layoutParams = lp
        layout.setBackgroundColor(context.resources.getColor(R.color.gray))
        layout.gravity = Gravity.CENTER
        startMove()

        val btn = MyBtn(context)
        layout.addView(btn.getButton())
    }

    // 이동 시 화면 벗어남 있음
    /*
    fun startMove() {
        layout.setOnTouchListener { v, event ->
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
     */

    // 이동 시 화면 벗어남 막음
    private fun startMove() {
        layout.setOnTouchListener { v, event ->
            var maxX = (v.parent as View).width - v.width.toFloat()
            var maxY = (v.parent as View).height - v.height.toFloat()
            var tx = x
            var ty = y
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = v.x - event.rawX
                    y = v.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    tx = event.rawX + x
                    if(tx < 0) tx = 0f else if(tx > maxX) tx = maxX
                    ty = event.rawY + y
                    if(ty < 0) ty = 0f else if(ty > maxY) ty = maxY
                    v.animate()
                            .x(tx)
                            .y(ty)
                            .setDuration(0)
                            .start()
                }
            }
            true
        }
    }

    fun getLayout(): LinearLayout {
        return layout
    }
}