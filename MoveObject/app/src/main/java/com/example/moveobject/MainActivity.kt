package com.example.moveobject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var btnList = mutableListOf<MyBtn>()
    var fixed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn.setOnClickListener {
            val myLayout = MyLayout(this)
            mainLayout.addView(myLayout.getLayout())
//            val newBtn = MyBtn(this)
//            btnList.add(newBtn)
//            mainLayout.addView(newBtn.getButton())
        }

        fixBtn.setOnClickListener {
            if(fixed) {
                for( btn in btnList) {
                    btn.fixedMove()
                }
            } else {
                for( btn in btnList) {
                    btn.startMove()
                }
            }
            fixed = !fixed
        }
    }
}