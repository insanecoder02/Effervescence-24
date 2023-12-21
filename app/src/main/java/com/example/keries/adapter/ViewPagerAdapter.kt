package com.example.keries.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
class ViewPagerAdapter(private val fragment: Fragment, private val fragmentClass:List<Class <out Fragment>>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentClass[position].newInstance()
    }
}