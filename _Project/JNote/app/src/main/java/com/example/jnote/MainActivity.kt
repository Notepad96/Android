package com.example.jnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jnote.DB.AppDataBase
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