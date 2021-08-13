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
    var w = dm.widthPixels * 15/16
    var h = dm.heightPixels * 15/16



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

    fun startMove() {
        layout.setOnTouchListener(ObjectMove())
    }

    fun stopMove() {
        layout.setOnTouchListener { v, event -> true }
    }

    fun getLayout(): LinearLayout {
        return layout
    }
}