package com.example.swipeview

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
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
        TabLayoutMediator(tabLayout0, viewPager) {
            tab, posiotion ->
            tab.setIcon(tabIconList[posiotion])
            tab.text = tabTextList[posiotion]
        }.attach()

//        tabLayout0.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.selected))


        tabLayout0.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var iconColor = ContextCompat.getColor(baseContext, R.color.selected)
                tab?.icon?.setColorFilter( iconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var iconColor = ContextCompat.getColor(baseContext, R.color.unSelected)
                tab?.icon?.setColorFilter( iconColor, PorterDuff.Mode.SRC_IN)

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                var iconColor = ContextCompat.getColor(baseContext, R.color.selected)
                tab?.icon?.setColorFilter( iconColor, PorterDuff.Mode.SRC_IN)
            }
        })
        tabLayout0.selectTab(tabLayout0.getTabAt(0))
    }
}