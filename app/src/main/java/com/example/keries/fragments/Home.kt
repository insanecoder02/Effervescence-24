package com.example.keries.fragments


import android.opengl.Visibility
import android.view.WindowManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.keries.others.AutoScrollManager

import com.example.keries.others.Constants
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import java.text.ParseException
import java.util.concurrent.TimeUnit
import java.util.logging.Handler



class Home : Fragment() {
    private lateinit var mainstageEventRecyclerView: RecyclerView
    private lateinit var mainStageEventAdapter : featuredEventsAdapter
    private  var aox  : MutableList<FeaturedEventes>  = mutableListOf()
    private lateinit var countdownTextView: TextView
    private lateinit var toolText : TextView
    private lateinit var logoTool : ImageView
    private lateinit var notifyTool : ImageView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val db = FirebaseFirestore.getInstance()
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false
    private  val bringmeDateboy  = Constants.MY_SET_DATE
    private val duration = 300 * 1000 // 300 seconds
    private val autoScrollHandler = android.os.Handler()
    private lateinit var autoScrollRunnable: Runnable
    private lateinit var alpha : LinearLayout
    private lateinit var beta: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alpha = view.findViewById(R.id.linearLayout)
        beta = view.findViewById(R.id.dayLinearLayout)


        mainstageEventRecyclerView = view.findViewById(R.id.FeaturedEventRecylerView)
        mainStageEventAdapter = featuredEventsAdapter(aox,this)
        mainstageEventRecyclerView.layoutManager =
            CarouselLayoutManager(true,true, 0.5F,true,true,true, LinearLayoutManager.HORIZONTAL)
        mainstageEventRecyclerView.adapter = mainStageEventAdapter
        (mainstageEventRecyclerView as CarouselRecyclerview).setInfinite(true)
        fetchFromFireStoreEvents("Main Stage",mainstageEventRecyclerView)



        val autoScrollManager = AutoScrollManager(mainstageEventRecyclerView)
        autoScrollManager.startAutoScroll(2000)

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener {
            Log.d("HomeFragment", "Swipe to refresh triggered")
            // Implement your refresh logic here
            fetchData()
        }

        fetchSystemDateTime()


        val notifyButotn = view.findViewById<ImageView>(R.id.imageView2)
        notifyButotn.setOnClickListener{loadFragment(notification())}
    }
    private fun fetchData() {
        Log.d("Home", "Fetching data...")
        this.fetchFromFireStoreEvents("Main Stage", mainstageEventRecyclerView)
        swipeRefreshLayout.isRefreshing = false
    }

    fun onItemClick(item: FeaturedEventes){
        val bundle=Bundle()
        bundle.putString("date" , item.date?:"Date")
        bundle.putString("details" , item.details?:"Details")
        bundle.putString("form" , item.form?:"Form")
        bundle.putString("name" , item.name?:"Name")
        bundle.putLong("no" , item.no?:123)
        bundle.putString("time" , item.time?:"Time")
        bundle.putString("url" , item.url?:"Url")
        bundle.putString("venue" , item.venue?:"Venue")
        val nextFragment = eventinfo()
        nextFragment.arguments=bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,nextFragment)
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
        try{
        val currentTimeMillis = System.currentTimeMillis()
        val targetDateString = bringmeDateboy // Replace with your target date
        Log.d("HomeFragment", "Target Date String: $targetDateString")
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val targetDate = sdf.parse(targetDateString)
        val timeDifferenceMillis = targetDate.time - currentTimeMillis

        startCountdown(timeDifferenceMillis)
    }
    catch (e: ParseException) {
        Toast.makeText(requireContext(),"this is not workign ",Toast.LENGTH_SHORT).show()
        Log.e("HomeFragment", "Error parsing date string", e)
        // Handle the exception or print a message for debugging
    }    }

    private fun startCountdown(timeInMillis: Long) {
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)%60

                val countdownText = String.format(
                    "%02d:%02d:%02d:%02d",
                    days, hours, minutes,seconds
                )
                updateUI(countdownText)
            }
            override fun onFinish() {
                alpha.visibility= ViewGroup.GONE
                beta.visibility = ViewGroup.GONE
                countDownTimer.cancel()
                isTimerRunning = false
            }
        }
        countDownTimer.start()
        isTimerRunning = true
    }
    private fun updateUI(countdownText: String) {
        val hourMinSec: List<String> = countdownText.split(":")
        view?.findViewById<TextView>(R.id.hours1)?.text = (hourMinSec[1].toInt() / 10).toString()
        view?.findViewById<TextView>(R.id.minutes1)?.text = (hourMinSec[2].toInt() / 10).toString()
        view?.findViewById<TextView>(R.id.days1)?.text = (hourMinSec[0].toInt() / 10).toString()
        view?.findViewById<TextView>(R.id.hours2)?.text = (hourMinSec[1].toInt() % 10).toString()
        view?.findViewById<TextView>(R.id.minutes2)?.text = (hourMinSec[2].toInt() % 10).toString()
        view?.findViewById<TextView>(R.id.days2)?.text = (hourMinSec[0].toInt() % 10).toString()

    }

    private fun stopCountdown() {
        if (isTimerRunning) {
            alpha.visibility= ViewGroup.GONE
            beta.visibility = ViewGroup.GONE
            countDownTimer.cancel()
            isTimerRunning = false
        }
    }

    private fun resetUI() {
        view?.findViewById<TextView>(R.id.days1)?.text = "00"
        view?.findViewById<TextView>(R.id.hours1)?.text = "00"
        view?.findViewById<TextView>(R.id.minutes1)?.text = "00"
        isTimerRunning = false

    }
    private fun fetchFromFireStoreEvents(eventType: String, recyclerView: RecyclerView) {
        aox.clear()
        // Fetch event data from Firestore for the specified event type
        db.collection(eventType)
            .get()
            .addOnSuccessListener { querySnapshot ->
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
                // Add the data to your existing adapter and notify it to update the RecyclerView
                aox.addAll(showeventlist)
                mainStageEventAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
    }
}
