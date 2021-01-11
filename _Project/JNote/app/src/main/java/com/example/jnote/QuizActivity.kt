package com.example.jnote

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.jnote.DB.Hanja
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {
    var position = 0
    var status = 0

    lateinit var quizList: List<Hanja>
    /* Setting */
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        /* Setting */
        sharedPref = getSharedPreferences("setting", MODE_PRIVATE)
        if(sharedPref.getBoolean("theme", false)) {
            setTheme(R.style.darkTheme)
        } else {
            setTheme(R.style.lightTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        // quizList = intent.getParcelableArrayExtra<Hanja>("quizList")
        quizList = intent.getSerializableExtra("quizList") as List<Hanja>
        quizList = quizList.shuffled()

        quizPhonation.text = quizList[position].phonation
    }

    fun next(view: View) {
        when(status) {
            0 -> {
                status = 1
                quizBtn.text = "다 음"
                quizWord.visibility = View.VISIBLE
                quizWord.text = quizList[position].word
                position++
            }
            1 -> {
                status = 0
                quizBtn.text = "정 답"
                quizWord.visibility = View.GONE
                quizPhonation.text = quizList[position].phonation
            }
        }
    }

}