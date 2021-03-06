package com.example.jnote

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jnote.DB.AppDataBase
import com.example.jnote.DB.Hanja
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
    private var levelList: List<Hanja>? = null
    private var beforeLevel = -2

    /* Setting */
    private lateinit var sharedPref: SharedPreferences
    private val requiredPermissions = arrayOf(
            Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        /* Setting */
        sharedPref = getSharedPreferences("setting", MODE_PRIVATE)
        if(sharedPref.getBoolean("theme", false)) {
            setTheme(R.style.darkTheme)
        } else {
            setTheme(R.style.lightTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()

        /* DB */
        db = AppDataBase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            if (db?.hanjaDao()?.getCount() == 0L) {
                createDatabase()
            }
        }

        /* Menu */
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navMenu.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item!!.itemId) {
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

    private fun checkPermissions() {
        var reject = mutableListOf<String>()

        for(permission in requiredPermissions) {
            if(ContextCompat.checkSelfPermission(this, permission) !==
                    PackageManager.PERMISSION_GRANTED) {
                reject.add(permission)
            }
        }

        if(reject.isNotEmpty()) {
            for (permission in reject) {
                ActivityCompat.requestPermissions(this, reject.toTypedArray(), 111)
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            111 -> {
                if(grantResults.isNotEmpty()) {
                    for((i, permission) in permissions.withIndex()) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {

                        }
                    }
                }
            }
        }
    }

    var preTime: Long = 0
    override fun onBackPressed() {
        val temp = System.currentTimeMillis()
        if(temp - preTime >= 3500L) {
            preTime = temp
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()
        }
    }

    private fun viewUpdate(level: Int) {
        sharedPref.edit {
            putInt("level", level)
        }
        titleUpdate(level)
        listUpdate(level)
    }

    private fun titleUpdate(level: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var count = when (level) {
                0 -> db?.hanjaDao()?.getCount()
                -1 -> db?.bookmarkDao()?.getCount()
                else -> db?.hanjaDao()?.getLevelCount(level)
            }
            CoroutineScope(Dispatchers.Main).launch {
                supportActionBar!!.title = when (level) {
                    0 -> "전체 보기 - ${count}개"
                    -1 -> "단어장"
                    else -> "Level $level - ${count}개"
                }
            }
        }
    }

    private fun btnTextUpdate() {
        mainBtn1.text = if (sharedPref.getBoolean("mode", true)) "단어 OFF"
        else "단어 ON"
        mainBtn2.text = if (sharedPref.getBoolean("mode2", true)) "뜻 OFF"
        else "뜻 ON"
    }

    private fun getList(level: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            levelList = when (level) {
                0 -> db?.hanjaDao()?.getListAll()
                -1 -> db?.bookmarkDao()?.getListAll()
                else -> db?.hanjaDao()?.getListLevel(level)
            }
        }
    }

    private fun listUpdate(level: Int, shuffle: Boolean = false) {
        if(beforeLevel != level) {
            getList(level)
            beforeLevel = level
        }
        btnTextUpdate()
        Thread.sleep(1000L)
        if (shuffle) {
            levelList = levelList?.shuffled()
        }

        if(level == -1) {
            viewAdapter = ListAdapter(this, levelList?.toMutableList(), sharedPref.getBoolean("mode", true), sharedPref.getBoolean("mode2", true), true)
        } else {
            viewAdapter = ListAdapter(this, levelList?.toMutableList(), sharedPref.getBoolean("mode", true), sharedPref.getBoolean("mode2", true))
        }

        recyclerView = cycleList.apply {
            adapter = viewAdapter
            layoutManager = viewManager
            setHasFixedSize(true)
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
        when (view.id) {
            R.id.mainBtn1 -> {
                if (sharedPref.getBoolean("mode", true)) {
                    sharedPref.edit { putBoolean("mode", false) }
                } else {
                    sharedPref.edit { putBoolean("mode", true) }
                }
                listUpdate(sharedPref.getInt("level", 0), false)
            }
            R.id.mainBtn2 -> {
                if (sharedPref.getBoolean("mode2", true)) {
                    sharedPref.edit { putBoolean("mode2", false) }
                } else {
                    sharedPref.edit { putBoolean("mode2", true) }
                }
                listUpdate(sharedPref.getInt("level", 0), false)
            }
            R.id.mainBtn3 -> listUpdate(sharedPref.getInt("level", 0), true)
            R.id.mainBtn4 -> {
                val intent = Intent(this, QuizActivity::class.java)
                var temp: ArrayList<Parcelable> = arrayListOf()
                levelList?.let { temp.addAll(it) }
                intent.putParcelableArrayListExtra("quizList", temp)

                startActivity(intent)
            }
        }
    }

    private fun reload() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.side_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> drawerMenu.openDrawer(GravityCompat.START)
            R.id.themeToggle -> {
                sharedPref.edit {
                    putBoolean("theme", !sharedPref.getBoolean("theme", false))
                }
                reload()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        db = null
        AppDataBase.deleteInstance()
        super.onDestroy()
    }

}