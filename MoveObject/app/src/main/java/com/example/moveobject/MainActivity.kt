package com.example.moveobject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var myLayout: MyLayout
    var fixed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn.setOnClickListener {
            myLayout = MyLayout(this)
            mainLayout.addView(myLayout.getLayout())
        }

        fixBtn.setOnClickListener {
            if(fixed) myLayout.stopMove()
            else myLayout.startMove()
            fixed = !fixed
        }
    }
}