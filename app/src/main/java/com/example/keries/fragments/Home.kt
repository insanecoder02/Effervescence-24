package com.example.keries.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
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
import com.google.firebase.firestore.FirebaseFirestore
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.keries.databinding.FragmentHomeBinding
import com.example.keries.others.AutoScrollManager
import com.example.keries.others.Constants
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import java.text.ParseException
import java.util.concurrent.TimeUnit

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainStageEventAdapter: featuredEventsAdapter
    private var aox: MutableList<FeaturedEventes> = mutableListOf()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false
    private val bringmeDateboy = Constants.MY_SET_DATE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainStageEventAdapter = featuredEventsAdapter(aox, this)
        binding.FeaturedEventRecylerView.layoutManager = CarouselLayoutManager(
            true, true, 0.5F, true, true, true, LinearLayoutManager.HORIZONTAL
        )

        binding.FeaturedEventRecylerView.adapter = mainStageEventAdapter
        (binding.FeaturedEventRecylerView as CarouselRecyclerview).setInfinite(true)
        fetchFromFireStoreEvents("Main Stage", binding.FeaturedEventRecylerView)

        val autoScrollManager = AutoScrollManager(binding.FeaturedEventRecylerView)
        autoScrollManager.startAutoScroll(2000)

        binding.swiperefresh.setOnRefreshListener {
            Log.d("HomeFragment", "Swipe to refresh triggered")
            fetchData()
        }

        fetchSystemDateTime()


//        val notifyButotn = view.findViewById<ImageView>(R.id.imageView2)
        binding.imageView2.setOnClickListener { loadFragment(notification()) }
    }

    private fun fetchData() {
        Log.d("Home", "Fetching data...")
        this.fetchFromFireStoreEvents("Main Stage", binding.FeaturedEventRecylerView)
        binding.swiperefresh.isRefreshing = false
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

    private fun fetchSystemDateTime() {
        try {
            val currentTimeMillis = System.currentTimeMillis()
            val targetDateString = bringmeDateboy // Replace with your target date
            Log.d("HomeFragment", "Target Date String: $targetDateString")
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val targetDate = sdf.parse(targetDateString)
            val timeDifferenceMillis = targetDate.time - currentTimeMillis

            startCountdown(timeDifferenceMillis)
        } catch (e: ParseException) {
            Toast.makeText(requireContext(), "this is not workign ", Toast.LENGTH_SHORT).show()
            Log.e("HomeFragment", "Error parsing date string", e)
        }
    }

    private fun startCountdown(timeInMillis: Long) {
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                val countdownText = String.format(
                    "%02d:%02d:%02d:%02d", days, hours, minutes, seconds
                )
                updateUI(countdownText)
            }

            override fun onFinish() {
                binding.linearLayout.visibility = ViewGroup.GONE
                binding.dayLinearLayout.visibility = ViewGroup.GONE
                countDownTimer.cancel()
                isTimerRunning = false
            }
        }
        countDownTimer.start()
        isTimerRunning = true
    }

    private fun updateUI(countdownText: String) {
        val hourMinSec: List<String> = countdownText.split(":")
        binding.hours1.text = (hourMinSec[1].toInt() / 10).toString()
        binding.minutes1.text = (hourMinSec[2].toInt() / 10).toString()
        binding.days1.text = (hourMinSec[0].toInt() / 10).toString()
        binding.hours2.text = (hourMinSec[1].toInt() % 10).toString()
        binding.minutes2.text = (hourMinSec[2].toInt() % 10).toString()
        binding.days2.text = (hourMinSec[0].toInt() % 10).toString()
    }

    private fun stopCountdown() {
        if (isTimerRunning) {
            binding.linearLayout.visibility = ViewGroup.GONE
            binding.dayLinearLayout.visibility = ViewGroup.GONE
            countDownTimer.cancel()
            isTimerRunning = false
        }
    }

    private fun resetUI() {
        binding.days1.text = "00"
        binding.hours1.text = "00"
        binding.minutes1.text = "00"
        isTimerRunning = false

    }

    private fun fetchFromFireStoreEvents(eventType: String, recyclerView: RecyclerView) {
        binding.loadMe.visibility = View.VISIBLE
        aox.clear()
        // Fetch event data from Firestore for the specified event type
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
                showeventlist.add(
                    FeaturedEventes(date, details, form, name, no, time, url, venue)
                )
            }
            aox.addAll(showeventlist)
            Handler(Looper.getMainLooper()).postDelayed({
                mainStageEventAdapter.notifyDataSetChanged()
                binding.loadMe.visibility = View.GONE
            }, 3000)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }
}
