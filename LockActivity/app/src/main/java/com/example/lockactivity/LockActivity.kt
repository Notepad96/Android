package com.example.lockactivity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_lock.*
import kotlinx.android.synthetic.main.activity_main.*

class LockActivity : AppCompatActivity() {
    var count = 0
    var password = arrayListOf<String>("-", "-", "-", "-", "-", "-")
    var viewText = arrayListOf<String>("○", "○", "○", "○", "○", "○")
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        sharedPref = getSharedPreferences("pwd", MODE_PRIVATE)
        sharedPref.edit {
            putString("pwd", "000000")
        }
        lockPwd.text = viewText.joinToString("")
    }


    fun numInput(view: View) {
        when(view.id) {
            R.id.lockDelete -> {
                viewText[--count] = "○"
            }
            R.id.lockReset -> {
                count = 0
                viewText = arrayListOf<String>("○", "○", "○", "○", "○", "○")
            }
            else -> {
                password[count++] = (view as Button).text.toString()
            }
        }
        for(i in 0 until count) viewText[i] = "●"

        lockPwd.text = viewText.joinToString("")

        if(count == 6) {
            if(password.joinToString("") == sharedPref.getString("pwd", null)) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                //Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "${password.joinToString("")}", Toast.LENGTH_SHORT).show()
                count = 0
                viewText = arrayListOf<String>("○", "○", "○", "○", "○", "○")
                lockPwd.text = viewText.joinToString("")
            }
        }
    }
}