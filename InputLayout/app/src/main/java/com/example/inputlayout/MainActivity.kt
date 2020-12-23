package com.example.inputlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* (text, start, before, count)
        text : 현재 EditText : CharSequence 타입
        start : 현재 입력한 문자가 위치한 Text Index
        before : 현재 입력하는 문자의 개수 (포커스 바뀌면 0부터 다시 시작, 삭제 시 1)
        count : Text 길이
         */
        EditID.doOnTextChanged() { text, start, before, count ->
            EditIDLayout.error = when(count) {
                in 1..2 -> "ID 를 3자 이상 입력해주세요."
                0, in 3..10 -> null
                else -> "ID 를 10자 이하로 입력해주세요."
            }
        }

        EditPwd.doOnTextChanged() { text, start, before, count ->
            EditPwdLayout.error = when(text?.length) {
                in 1..5 -> "Password 를 6자 이상 입력해주세요."
                0, in 6..22 -> null
                else -> "Password 를 22자 이하로 입력해주세요."
            }
        }

    }
}