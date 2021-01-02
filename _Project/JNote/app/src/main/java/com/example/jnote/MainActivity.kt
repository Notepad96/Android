package com.example.jnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jnote.DB.AppDataBase
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var db: AppDataBase? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* 메뉴 */
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        supportActionBar!!.title = "전체 보기"

        navMenu.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {item: MenuItem ->
            when(item!!.itemId) {

            }
            drawerMenu.closeDrawers()
            false
        })

        /* 리스트 및 DB 생성 */
        viewManager = LinearLayoutManager(this)
        db = AppDataBase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            if(db?.hanjaDao()?.getCount() == 0L ) {
                createDatabase()
            }
            viewAdapter = ListAdapter(db?.hanjaDao()?.getListAll())
            CoroutineScope(Dispatchers.Main).launch {
                recyclerView = cycleList.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.side_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            android.R.id.home -> drawerMenu.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        db = null
        AppDataBase.deleteInstance()
        super.onDestroy()
    }

    fun createDatabase() {
        val inputStream = resources.assets.open("hanja.txt")
        inputStream.bufferedReader().readLines().forEach {
            var line = it.trim().split("\t").map { it.trim { it <= '\"' } }

            CoroutineScope(Dispatchers.IO).launch {
                db?.hanjaDao()?.insertHanja(line[0].toInt(), line[1], line[2], line[3])
            }
        }
    }

}