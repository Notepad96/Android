package com.example.moveobject

import android.view.MotionEvent
import android.view.View

class ObjectMove: View.OnTouchListener {
    var x = 0f
    var y = 0f

    // 이동 시 화면 벗어남 막음
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var maxX = (v?.parent as View).width - v.width.toFloat()
        var maxY = (v.parent as View).height - v.height.toFloat()
        var tx = x
        var ty = y
        when(event?.action) {
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
        return true
    }

    // 이동 시 화면 벗어남 있음
    /*
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
     */
}