package com.example.jnote

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
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

        quizCount.text = "${position+1} / ${quizList.size}"
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
                if(position == quizList.size) {
                    AlertDialog.Builder(this, R.style.AlertDialog).apply {
                        setTitle("퀴즈가 종료되었습니다.")
                        setPositiveButton("완료", DialogInterface.OnClickListener { dialog, which ->
                            finish()
                        })
                    }.show()
                } else {
                    status = 0
                    quizBtn.text = "정 답"
                    quizCount.text = "${position+1} / ${quizList.size}"
                    quizWord.visibility = View.GONE
                    quizPhonation.text = quizList[position].phonation
                }
            }
        }
    }

}