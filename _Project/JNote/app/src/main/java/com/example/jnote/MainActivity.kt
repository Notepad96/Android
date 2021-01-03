package com.example.jnote

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
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
    /* DB */
    private var db: AppDataBase? = null

    /* List */
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    /* 설정 */
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* Setting */
        sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        /* DB */
        db = AppDataBase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            if(db?.hanjaDao()?.getCount() == 0L ) {
                createDatabase()
            }
        }

        /* Menu */
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        navMenu.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {item: MenuItem ->
            when(item!!.itemId) {
                R.id.showAll -> viewUpdate(0)
                R.id.showStar -> viewUpdate(-1)
                R.id.level1 -> viewUpdate(1)
                R.id.level2 -> viewUpdate(2)
                R.id.level3 -> viewUpdate(3)
                R.id.level4 -> viewUpdate(4)
                R.id.level5 -> viewUpdate(5)
                R.id.level6 -> viewUpdate(6)
            }
            drawerMenu.closeDrawers()
            false
        })

        /* View & List */
        viewManager = LinearLayoutManager(this)
        viewUpdate(sharedPref.getInt("level", 0))
    }

    private fun viewUpdate(level: Int) {
        sharedPref.edit {
            putInt("level", level)
        }
        listUpdate(level)
        titleUpdate(level)
    }

    private fun titleUpdate(level: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var count = when(level) {
                0 -> db?.hanjaDao()?.getCount()
                -1 -> db?.bookmarkDao()?.getCount()
                else -> db?.hanjaDao()?.getLevelCount(level)
            }
            CoroutineScope(Dispatchers.Main).launch {
                supportActionBar!!.title = when(level) {
                    0 -> "전체 보기 - ${count}개"
                    -1 -> "즐겨 찾기 - ${count}개"
                    else -> "Level $level - ${count}개"
                }
            }
        }
    }

    private fun btnTextUpdate() {
        mainBtn1.text = if(sharedPref.getBoolean("mode", true)) "단어 OFF"
        else "단어 ON"
        mainBtn2.text = if(sharedPref.getBoolean("mode2", true)) "뜻 OFF"
        else "뜻 ON"
    }

    private fun listUpdate(level: Int, shuffle: Boolean = false) {
        btnTextUpdate()
        CoroutineScope(Dispatchers.IO).launch {
            val levelList = when(level) {
                0 -> db?.hanjaDao()?.getListAll()
                -1 -> db?.bookmarkDao()?.getListAll()
                else -> db?.hanjaDao()?.getListLevel(level)
            }

            viewAdapter = if(shuffle) {
                ListAdapter(levelList?.shuffled(), sharedPref.getBoolean("mode", true), sharedPref.getBoolean("mode2", true))
            } else {
                ListAdapter(levelList, sharedPref.getBoolean("mode", true), sharedPref.getBoolean("mode2", true))
            }

            CoroutineScope(Dispatchers.Main).launch {
                recyclerView = cycleList.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
            }
        }
    }

    private fun createDatabase() {
        val inputStream = resources.assets.open("hanja.txt")
        inputStream.bufferedReader().readLines().forEach {
            var line = it.trim().split("\t").map { it.trim { it <= '\"' } }

            CoroutineScope(Dispatchers.IO).launch {
                db?.hanjaDao()?.insertHanja(line[0].toInt(), line[1], line[2], line[3])
            }
        }
    }

    fun buttonEvent(view: View) {
        when(view.id) {
            R.id.mainBtn1 -> {
                if(sharedPref.getBoolean("mode", true)) {
                    sharedPref.edit { putBoolean("mode", false) }
                } else {
                    sharedPref.edit { putBoolean("mode", true) }
                }
                listUpdate(sharedPref.getInt("level", 0))
            }
            R.id.mainBtn2 -> {
                if(sharedPref.getBoolean("mode2", true)) {
                    sharedPref.edit { putBoolean("mode2", false) }
                } else {
                    sharedPref.edit { putBoolean("mode2", true) }
                }
                listUpdate(sharedPref.getInt("level", 0))
            }
            R.id.mainBtn3 -> listUpdate(sharedPref.getInt("level", 0), true)
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

}