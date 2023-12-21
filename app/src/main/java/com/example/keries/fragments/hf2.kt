package com.example.keries.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keries.R
import com.example.keries.adapter.featuredEventsAdapter
import com.example.keries.dataClass.FeaturedEventes
import com.example.keries.others.AutoScrollManager
import com.google.firebase.firestore.FirebaseFirestore
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview


class hf2 : Fragment() {
    private lateinit var mainStageEventAdapter: featuredEventsAdapter
    private var aox: MutableList<FeaturedEventes> = mutableListOf()
    private val cache = mutableMapOf<String, List<FeaturedEventes>>()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var FeaturedEventRecylerView: RecyclerView
    private lateinit var loadMe: View
    private val autoScrollManagers = mutableListOf<AutoScrollManager>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.hf2_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FeaturedEventRecylerView = view.findViewById(R.id.FeaturedEventRecylerView)
        loadMe = view.findViewById(R.id.loadMe)
        mainStageEventAdapter = featuredEventsAdapter(aox, this)
        FeaturedEventRecylerView.layoutManager = CarouselLayoutManager(
            true, true, 0.5F, true, true, true, LinearLayoutManager.HORIZONTAL
        )


        FeaturedEventRecylerView.adapter = mainStageEventAdapter
        (FeaturedEventRecylerView as CarouselRecyclerview).setInfinite(true)
        fetchFromFireStoreEvents("Main Stage", FeaturedEventRecylerView)

        val autoScrollManager = AutoScrollManager(FeaturedEventRecylerView)
        autoScrollManager.startAutoScroll(2000)
        autoScrollManagers.add(autoScrollManager)
    }

    override fun onPause() {
        super.onPause()
        autoScrollManagers.forEach { it.stopAutoScroll() }
    }

    private fun fetchData() {
        Log.d("Home", "Fetching data...")
        this.fetchFromFireStoreEvents("Main Stage", FeaturedEventRecylerView)
    }
    fun onItemClick(item: FeaturedEventes) {
        val bundle = Bundle()
        bundle.putString("date", item.date ?: "Date")
        bundle.putString("details", item.details ?: "Details")
        bundle.putString("form", item.form ?: "Form")
        bundle.putString("name", item.name ?: "Name")
        bundle.putLong("no", item.no ?: 123)
        bundle.putString("time", item.time ?: "Time")
        bundle.putString("url", item.url ?: "Url")
        bundle.putString("venue", item.venue ?: "Venue")
        bundle.putString("live", item.live ?: "live")
        bundle.putString("soc", item.societyName ?: "soc")

        val nextFragment = eventinfo()
        nextFragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, nextFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun loadFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Add to back stack so you can navigate back
            .commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchFromFireStoreEvents(eventType: String, recyclerView: RecyclerView) {
        loadMe.visibility = View.VISIBLE
        if (cache.containsKey(eventType)) {
            val cachedData = cache[eventType]
            if (cachedData != null) {
                aox.clear()
                aox.addAll(cachedData)
                mainStageEventAdapter.notifyDataSetChanged()
                loadMe.visibility = View.GONE
                return
            }
        }
        db.collection(eventType).get().addOnSuccessListener { querySnapshot ->
            val showeventlist = mutableListOf<FeaturedEventes>()
            for (document in querySnapshot) {
                val date = document.getString("date") ?: ""
                val details = document.getString("details") ?: ""
                val form = document.getString("form") ?: ""
                val name = document.getString("name") ?: ""
                val no = document.getLong("no") ?: 0
                val time = document.getString("time") ?: ""
                val url = document.getString("url") ?: ""
                val venue = document.getString("venue") ?: ""
                val live = document.getString("live") ?: ""
                val socityname = document.getString("nameofSoc") ?: ""
                showeventlist.add(
                    FeaturedEventes(
                        date,
                        details,
                        form,
                        name,
                        no,
                        time,
                        url,
                        venue,
                        live,
                        socityname
                    )
                )
            }
            aox.clear()
            aox.addAll(showeventlist)
            mainStageEventAdapter.notifyDataSetChanged()
            loadMe.visibility = View.GONE

            cache[eventType] = showeventlist
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }
}