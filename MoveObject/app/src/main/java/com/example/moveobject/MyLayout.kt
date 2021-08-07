package com.example.moveobject

import android.content.Context
import android.widget.LinearLayout


class MyLayout(context: Context) {
    private var layout = LinearLayout(context)
    var lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)

    init {
        layout.layoutParams = lp
        layout.setBackgroundColor(getLayout().resources.getColor(R.color.gray))
    }

    fun getLayout(): LinearLayout {
        return layout
    }
}