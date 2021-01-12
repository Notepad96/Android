package com.example.jnote

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.jnote.DB.AppDataBase
import com.example.jnote.DB.Hanja
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity() {
    var position = 0
    var status = 0
    var color = true
    private var db: AppDataBase? = null

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

        db = AppDataBase.getInstance(this)
        drawBoard.addView(PaintBoard(this))

        quizList = intent.getSerializableExtra("quizList") as List<Hanja>
        quizList = quizList.shuffled()

        quizCount.text = "${position+1} / ${quizList.size}"
        quizPhonation.text = quizList[position].phonation
    }

    fun changeColor(view: View) {
        if(color) {

        }
    }

    fun addBookmark(view: View) {
        Snackbar.make(view, "단어장에 추가되었습니다.", Snackbar.LENGTH_LONG).show()
        CoroutineScope(Dispatchers.IO).launch {
            db?.bookmarkDao()?.insertHanja(quizList[position].level,
                    quizList[position].word ?: "",
                    quizList[position].phonation ?: "",
                    quizList[position].mean ?: "")
        }
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