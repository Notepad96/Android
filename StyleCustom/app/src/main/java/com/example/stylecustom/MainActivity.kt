package com.example.stylecustom

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var sharePref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharePref = this.getPreferences(Context.MODE_PRIVATE)
        if(sharePref.getBoolean("dark", false)) {
            setTheme(R.style.DarkMode)
        } else {
            setTheme(R.style.Basic)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switch1.isChecked = sharePref.getBoolean("dark", false)

        switch1.setOnCheckedChangeListener { _, isChecked ->
            sharePref.edit {
                if(isChecked) {
                    this.putBoolean("dark", true)
                }
                else {
                    this.putBoolean("dark", false)
                }
            }
            reload()
        }
    }

    private fun reload() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

}