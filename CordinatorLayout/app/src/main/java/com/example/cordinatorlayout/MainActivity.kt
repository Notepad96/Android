package com.example.cordinatorlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        toolbar.title = "TEST TEXT"
//        setSupportActionBar(toolbar)
//        if(supportActionBar != null) supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}