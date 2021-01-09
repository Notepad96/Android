package com.example.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences("setting", MODE_PRIVATE)
        // sharedPref = this.getPreferences(Context.MODE_PRIVATE) // 해당 엑티비티에서만
        textView.text = sharedPref.getString("opt1", null)  // (Key, Default Value)
    }

    fun change(v: View) {
        sharedPref.edit {
            putString("opt1", editText.text.toString())
            editText.setText("")
        }
        textView.text = sharedPref.getString("opt1", null)
    }
}