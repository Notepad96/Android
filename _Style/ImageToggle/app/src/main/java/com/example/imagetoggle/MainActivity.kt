package com.example.imagetoggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageButton.setOnClickListener { v ->
            v.isSelected = !v.isSelected
            Toast.makeText(this, "${v.isSelected}", Toast.LENGTH_SHORT).show()
        }
    }
}