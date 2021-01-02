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
        supportActionBar!!.title = "전체 보기 ()개"

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

        /* DB 생성 및 초기화 */
        db = AppDataBase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            if(db?.hanjaDao()?.getCount() == 0L ) {
                createDatabase()
            }
        }

        /* 리스트 */
        viewManager = LinearLayoutManager(this)
        listUpdate(1)
    }

    private fun viewUpdate(level: Int) {
        titleUpdate(level)
        listUpdate(level)
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
                    0 -> "전체 보기 ${count}개"
                    -1 -> "즐겨 찾기 ${count}개"
                    else -> "Level $level ${count}개"
                }
            }
        }
    }

    private fun listUpdate(level: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val levelList = when(level) {
                0 -> db?.hanjaDao()?.getListAll()
                -1 -> db?.bookmarkDao()?.getListAll()
                else -> db?.hanjaDao()?.getListLevel(level)
            }
            viewAdapter = ListAdapter(levelList)
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