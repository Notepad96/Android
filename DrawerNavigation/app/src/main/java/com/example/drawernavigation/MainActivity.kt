package com.example.drawernavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)    // 액티비티에서 툴바 사용하겠다고 명시
        //supportActionBar!!.setDisplayShowTitleEnabled(false)  // Title 사용 X
        supportActionBar!!.title = "GO" // Title 변경
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // home 버튼 사용 여부
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24) // home 버튼 이미지


        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
            when(item!!.itemId) {
                R.id.test -> textMain.text = "Test1"
                R.id.test2 -> textMain.text = "Test2"
                R.id.test3 -> textMain.text = "Test3"
            }
            drawer_layout.closeDrawers()
            false
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            android.R.id.home -> drawer_layout.openDrawer(GravityCompat.START)
            R.id.item1 -> textMain.text = "Item1"
            R.id.item2 -> textMain.text = "Item2"
            R.id.item3 -> textMain.text = "Item3"
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

}