package com.example.keries.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.keries.fragments.ExploreClickListener
import com.example.keries.fragments.hf1
import com.example.keries.fragments.hf2

class ViewPagerAdapter(private val fragment: Fragment, private val fragmentClass:List<Class <out Fragment>>, private val exploreClickListener: ExploreClickListener) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragmentClass = fragmentClass[position]
        return when (fragmentClass) {
            hf1::class.java -> {
                val fragment = hf1()
                fragment.setExploreClickListener(exploreClickListener)
                fragment
            }
            hf2::class.java -> {
                val fragment = hf2()
//                fragment.setExploreClickListener(exploreClickListener)
                fragment
            }
            // ... (Create other fragments as needed)
            else -> throw IllegalArgumentException("Unknown fragment class: $fragmentClass")
        }    }
}