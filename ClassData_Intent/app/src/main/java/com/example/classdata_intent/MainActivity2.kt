package com.example.classdata_intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var str = ""
        if(intent.getIntExtra("action", 1) == 1) {
            val data = intent.getParcelableExtra<Book>("data")
            str = data.toString()
        } else {
            val dataList = intent.getSerializableExtra("dataList") as List<Book>
            Toast.makeText(this, "${dataList[1].name}:${dataList[1].price}", Toast.LENGTH_SHORT).show()
            dataList.forEach { str += it.toString() + "\n" }
        }

        textView.text = str
    }
}