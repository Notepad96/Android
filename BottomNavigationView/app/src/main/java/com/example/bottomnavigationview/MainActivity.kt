package com.example.bottomnavigationview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.mainLayout, Fragment1()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainLayout, Fragment1()).commit()
            }
            R.id.service -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainLayout, Fragment2()).commit()
            }
            R.id.setting -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainLayout, Fragment3()).commit()
            }
        }
        return true
    }

}