package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun open(view: View) {
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true

            loadUrl("https://ja.dict.naver.com/#/search?query=%E6%8E%A5")
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