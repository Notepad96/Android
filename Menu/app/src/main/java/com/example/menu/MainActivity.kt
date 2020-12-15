package com.example.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var intent = Intent(this, MenuLayout::class.java)
        when(item.itemId) {
            R.id.one -> intent.putExtra("level", "1")
            R.id.two -> intent.putExtra("level", "2")
            R.id.three -> intent.putExtra("level", "3")
            R.id.four -> intent.putExtra("level", "4")
            R.id.five -> intent.putExtra("level", "5")
            R.id.six -> intent.putExtra("level", "6")
        }
        startActivity(intent)
        return true
    }

}