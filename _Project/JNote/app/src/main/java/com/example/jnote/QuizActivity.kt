package com.example.jnote

import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
    private var db: AppDataBase? = null
    private lateinit var paintboard: PaintBoard

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
        paintboard = PaintBoard(this)
        drawBoard.addView(paintboard)
        if(sharedPref.getBoolean("theme", false)) {
            paintboard.paint.color = Color.WHITE
        } else {
            paintboard.paint.color = ContextCompat.getColor(this, R.color.darkFirstColorVariant)
        }

        quizList = intent.getSerializableExtra("quizList") as List<Hanja>
        quizList = quizList.shuffled()

        quizCount.text = "${position+1} / ${quizList.size}"
        quizPhonation.text = quizList[position].phonation

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this, R.style.AlertDialog2).apply {
            setTitle("퀴즈를 종료하겠습니까?")
            setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                super.onBackPressed()
            })
            setNegativeButton("아니요", DialogInterface.OnClickListener { dialog, which ->

            })
        }.show()
    }

    fun clearBoard(view: View) {
        paintboard.path.rewind()
        paintboard.invalidate()
    }

    fun addBookmark(view: View) {
        Snackbar.make(view, "단어장에 추가되었습니다.", Snackbar.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            if(status == 0 ) {
                db?.bookmarkDao()?.insertHanja(quizList[position].level,
                        quizList[position].word ?: "",
                        quizList[position].phonation ?: "",
                        quizList[position].mean ?: "")
            } else {
                db?.bookmarkDao()?.insertHanja(quizList[position-1].level,
                        quizList[position-1].word ?: "",
                        quizList[position-1].phonation ?: "",
                        quizList[position-1].mean ?: "")
            }
        }
    }

    fun next(view: View) {
        when(status) {
            0 -> {
                status = 1
                quizBtn.text = "다 음"
                quizWord.visibility = View.VISIBLE
                quizWord.text = quizList[position].word
                drawBoard.invalidate()
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
                    quizWord.visibility = View.INVISIBLE
                    quizPhonation.text = quizList[position].phonation
                    paintboard.path.rewind()
                    paintboard.invalidate()
                }
            }
        }
    }

}