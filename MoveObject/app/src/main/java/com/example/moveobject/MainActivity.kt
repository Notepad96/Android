package com.example.moveobject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var myLayout: MyLayout
    var fixed = true

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn.setOnClickListener {
            myLayout = MyLayout(this)
            mainLayout.addView(myLayout.getLayout())
            text01.text = resources.getXml(R.layout.activity_main).toString()
        }

        fixBtn.setOnClickListener {
            if(fixed) myLayout.stopMove()
            else myLayout.startMove()
            fixed = !fixed
        }
    }
}