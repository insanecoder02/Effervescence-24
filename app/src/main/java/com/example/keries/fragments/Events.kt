package com.example.keries.fragments

import android.util.Log
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keries.R
import com.example.keries.adapter.ShowEventAdapter
import com.example.keries.dataClass.Event_DataClass
import com.example.keries.databinding.FragmentEventsBinding
import com.example.keries.others.AutoScrollManager
import com.google.firebase.firestore.FirebaseFirestore

class Events : Fragment() {
    private lateinit var binding: FragmentEventsBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var showEventAdapter: ShowEventAdapter
    private var ij: MutableList<Event_DataClass> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showEventAdapter = ShowEventAdapter(ij, this)

        binding.amsRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.amsRV.adapter = showEventAdapter
        fetchFromFireStoreEvents("AMS", binding.amsRV)
        binding.geneticxRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.geneticxRV.adapter = showEventAdapter
        fetchFromFireStoreEvents("Dance", binding.geneticxRV)
        binding.rangtaringiniRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rangtaringiniRV.adapter = showEventAdapter
        fetchFromFireStoreEvents("Dramatics", binding.rangtaringiniRV)
        binding.nimritiRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.nimritiRV.adapter = showEventAdapter
        fetchFromFireStoreEvents("Fine Arts", binding.nimritiRV)
        binding.sarasvaRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.sarasvaRV.adapter = showEventAdapter
        fetchFromFireStoreEvents("Literature", binding.sarasvaRV)
        binding.virtuosiRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.virtuosiRV.adapter = showEventAdapter
        fetchFromFireStoreEvents("Music", binding.virtuosiRV)
        binding.gamingRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.gamingRv.adapter = showEventAdapter
        fetchFromFireStoreEvents("Gaming", binding.gamingRv)
        binding.InformalRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.InformalRv.adapter = showEventAdapter
        fetchFromFireStoreEvents("Informal", binding.InformalRv)

        rotor(binding.amsRV)
        rotor(binding.geneticxRV)
        rotor(binding.rangtaringiniRV)
        rotor(binding.nimritiRV)
        rotor(binding.sarasvaRV)
        rotor(binding.virtuosiRV)
        rotor(binding.gamingRv)
        rotor(binding.InformalRv)
    }

    private fun rotor(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val autoScrollManager = AutoScrollManager(recyclerView)
        autoScrollManager.startAutoScroll(2000)
    }

    fun onItemClick(item: Event_DataClass) {
        Log.d(
            "Events",
            "Date: ${item.date}, Details: ${item.details}, Form: ${item.form}, Name: ${item.name}, No: ${item.no}, Time: ${item.time}, URL: ${item.url}, Venue: ${item.venue}"
        )
        val bundle = Bundle()
        bundle.putString("date", item.date ?: "Date")
        bundle.putString("details", item.details)
        bundle.putString("form", item.form ?: "Form")
        bundle.putString("name", item.name ?: "Name")
        bundle.putLong("no", item.no ?: 123)
        bundle.putString("time", item.time ?: "Time")
        bundle.putString("url", item.url ?: "Url")
        bundle.putString("venue", item.venue ?: "Venue")
        val nextFragment = eventinfo()
        nextFragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.fragment_container, nextFragment
        ) // Use nextFragment instead of basefragmentevent()
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun fetchFromFireStoreEvents(eventType: String, recyclerView: RecyclerView) {
        binding.loadMeevent.visibility = View.VISIBLE
        ij.clear()
        db.collection(eventType).get().addOnSuccessListener {
            val showeventlist = mutableListOf<Event_DataClass>()
            for (document in it) {
                val date = document.getString("date") ?: ""
                val details = document.getString("details") ?: ""
                val form = document.getString("form") ?: ""
                val name = document.getString("name") ?: ""
                val no = document.getLong("no") ?: 0
                val time = document.getString("time") ?: ""
                val url = document.getString("url") ?: ""
                val venue = document.getString("venue") ?: ""
                showeventlist.add(
                    Event_DataClass(date, details, form, name, no, time, url, venue)
                )
            }
            Handler(Looper.getMainLooper()).postDelayed({
                showEventAdapter = ShowEventAdapter(showeventlist, this)
                recyclerView.adapter = showEventAdapter
                binding.eventsConstraint.visibility = View.VISIBLE
                binding.loadMeevent.visibility = View.GONE
            }, 3000)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }
}
