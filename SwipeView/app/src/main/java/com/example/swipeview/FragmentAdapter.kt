package com.example.swipeview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentActivity: FragmentActivity, val count: Int) : FragmentStateAdapter(fragmentActivity)
{
    override fun getItemCount(): Int = count

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Fragment1()
            1 -> Fragment2()
            else -> Fragment3()
        }
    }
}