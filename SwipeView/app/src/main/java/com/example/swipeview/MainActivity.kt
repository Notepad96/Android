package com.example.swipeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tabTextList = arrayListOf("FM1", "FM2", "FM3")
    private val tabIconList = arrayListOf(R.drawable.ic_baseline_access_alarms_24,
    R.drawable.ic_baseline_assignment_24,
    R.drawable.ic_baseline_duo_24)
    var size = tabTextList.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        viewPager.adapter = FragmentAdapter(this, size)
        TabLayoutMediator(tabLayout, viewPager) {
            tab, posiotion ->
            tab.setIcon(tabIconList[posiotion])
            tab.text = tabTextList[posiotion]
        }.attach()
    }
}