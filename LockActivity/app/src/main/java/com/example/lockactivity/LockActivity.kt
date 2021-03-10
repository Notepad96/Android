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

        sharedPref = getSharedPreferences("setting", MODE_PRIVATE)
        if(sharedPref.getString("pwd", null) == null) {
            textInfo.visibility = View.VISIBLE
            textInfo.text = when(intent.getIntExtra("second", 0)) {
                0 -> "비밀번호를 설정해 주세요."
                1 -> "비밀번호 확인"
                else -> "비밀번호가 일치하지 않습니다!"
            }
        } else {
            textInfo.visibility = View.GONE
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
            val pwd = password.joinToString("")
            if(sharedPref.getString("pwd", null) == null) {
                var retry = Intent(this, LockActivity::class.java)
                if(intent.getStringExtra("password") == pwd) {
                    Toast.makeText(this, "${pwd}/${intent.getStringExtra("password")}", Toast.LENGTH_SHORT).show()
                    sharedPref.edit {
                        putString("pwd", pwd)
                    }
                } else {
                    when(intent.getIntExtra("second", 0) ) {
                        0 -> {
                            retry.putExtra("password", pwd)
                            retry.putExtra("second", 1)
                        }
                        else -> {
                            retry.putExtra("password", intent.getStringExtra("password"))
                            retry.putExtra("second", 2)
                        }
                    }
                }
                startActivity(retry)
                finish()
            }
            else if(pwd == sharedPref.getString("pwd", null)) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "접속 성공!!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
//                Toast.makeText(this, "${pwd}/${sharedPref.getString("pwd", null)}", Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "$pwd", Toast.LENGTH_SHORT).show()
                count = 0
                viewText = arrayListOf<String>("○", "○", "○", "○", "○", "○")
                lockPwd.text = viewText.joinToString("")
                textInfo.visibility = View.VISIBLE
                textInfo.text = "비밀번호가 일치하지 않습니다!"
            }
        }
    }

}