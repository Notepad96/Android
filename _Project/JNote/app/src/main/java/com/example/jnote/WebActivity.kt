package com.example.jnote

import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_web)

        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://ja.dict.naver.com/#/search?query=${intent.getStringExtra("word")}")
        }
    }

    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}