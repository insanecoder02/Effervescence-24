package com.example.keries.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.keries.dataClass.Event_DataClass
import com.example.keries.fragments.Events
import com.example.keries.fragments.eventinfo

class FragmentAdapter(
    lifecycle: FragmentManager,
    fragmentManager: Lifecycle
) : FragmentStateAdapter(lifecycle,fragmentManager){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
//         if(position==0){
        return  eventinfo()
//        }//
//        else{
//            evenreel()
        }

}