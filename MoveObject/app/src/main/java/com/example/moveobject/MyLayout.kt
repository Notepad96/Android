package com.example.moveobject

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView


class MyLayout(context: Context) {
    private var layout = LinearLayout(context)
    var lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
    var dm = context.resources.displayMetrics
    var w = dm.widthPixels * 1/2
    var h = w * 192/124

    init {
        lp.width = w
        lp.height = h
        lp.gravity = Gravity.CENTER
        layout.layoutParams = lp
        layout.setBackgroundColor(context.resources.getColor(R.color.gray))

        val btn = MyBtn(context)
        layout.addView(btn.getButton())
    }

    fun getLayout(): LinearLayout {
        return layout
    }
}