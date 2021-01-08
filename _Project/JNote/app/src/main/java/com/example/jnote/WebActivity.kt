package com.example.jnote

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        Toast.makeText(this, "${intent.getStringExtra("word")}", Toast.LENGTH_SHORT).show()

        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true

        //webView.loadUrl("https://ja.dict.naver.com/#/search?query=${intent.getStringExtra("word")}")
        webView.loadUrl("https://google.com")
    }
}