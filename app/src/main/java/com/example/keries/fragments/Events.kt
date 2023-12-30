package com.example.keries.fragments


import android.os.Bundle
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Events : Fragment() {
    private lateinit var binding: FragmentEventsBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var showEventAdapter: ShowEventAdapter
    private var ij: MutableList<Event_DataClass> = mutableListOf()
    private val cache = mutableMapOf<String, List<Event_DataClass>>()
    private val autoScrollManagers = mutableListOf<AutoScrollManager>()

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
        autoScrollManagers.add(autoScrollManager)
    }

    override fun onPause() {
        super.onPause()
        autoScrollManagers.forEach { it.stopAutoScroll() }
    }

    fun onItemClick(item: Event_DataClass) {
        val bundle = Bundle()
        bundle.putString("date", item.date ?: "Date")
        bundle.putString("details", item.details)
        bundle.putString("form", item.form ?: "")
        bundle.putString("name", item.name ?: "Name")
        bundle.putLong("no", item.no ?: 123)
        bundle.putString("time", item.time ?: "Time")
        bundle.putString("url", item.url ?: "Url")
        bundle.putString("venue", item.venue ?: "Venue")
        bundle.putString("live", item.live ?: "Venue")
        bundle.putString("soc", item.societyName ?: "soc")
        val nextFragment = eventinfo()
        nextFragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.fragment_container, nextFragment
        ) // Use nextFragment instead of basefragmentevent()
        transaction.addToBackStack(null)
        transaction.commit()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchFromFireStoreEvents(eventType: String, recyclerView: RecyclerView) {
        binding.loadMeevent.visibility = View.VISIBLE
        ij.clear()
        if (cache.containsKey(eventType)) {
            val cachedData = cache[eventType]
            showEventAdapter = ShowEventAdapter(cachedData, this@Events)
            recyclerView.adapter = showEventAdapter
            binding.eventsConstraint.visibility = View.VISIBLE
            binding.loadMeevent.visibility = View.GONE
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val querySnapshot = db.collection(eventType).get().await()
                    val showeventlist = mutableListOf<Event_DataClass>()
                    for (document in querySnapshot) {
                        val date = document.getString("date") ?: ""
                        val details = document.getString("details") ?: ""
                        val form = document.getString("form") ?: ""
                        val name = document.getString("name") ?: ""
                        val no = document.getLong("no") ?: 0
                        val time = document.getString("time") ?: ""
                        val url = document.getString("url") ?: ""
                        val venue = document.getString("venue") ?: ""
                        val live = document.getString("Live") ?: ""
                        val socityname = document.getString("Society Name") ?: ""
                        showeventlist.add(
                            Event_DataClass(
                                date, details, form, name, no, time, url, venue, live, socityname
                            )
                        )
                    }
                    cache[eventType] = showeventlist
                    showEventAdapter = ShowEventAdapter(showeventlist, this@Events)
                    recyclerView.adapter = showEventAdapter
                    binding.eventsConstraint.visibility = View.VISIBLE
                    binding.loadMeevent.visibility = View.GONE
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
