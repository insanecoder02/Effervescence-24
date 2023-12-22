package com.example.keries.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.keries.R
import com.example.keries.databinding.FragmentHomeBinding
import com.example.keries.others.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit


interface ExploreClickListener {
    fun onExploreClick()
}



private lateinit var countDownTimer: CountDownTimer
private lateinit var commingsoon: TextView
private var isTimerRunning = false
private val bringmeDateboy = Constants.MY_SET_DATE
private lateinit var days1: TextView
private lateinit var hours1: TextView
private lateinit var minutes1: TextView
private lateinit var days2: TextView
private lateinit var hours2: TextView
private lateinit var minutes2: TextView
private lateinit var linearLayout: ViewGroup
private lateinit var dayLinearLayout: ViewGroup
private lateinit var congo: TextView
private lateinit var exploreClickListener: ExploreClickListener

class hf1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.hf1_layout, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val explore = view.findViewById<TextView>(R.id.explore)
        explore.setOnClickListener{
            exploreClickListener.onExploreClick()
        }
        commingsoon = view.findViewById(R.id.congo)
        days1 = view.findViewById(R.id.days1)
        hours1 = view.findViewById(R.id.hours1)
        minutes1 = view.findViewById(R.id.minutes1)
        days2 = view.findViewById(R.id.days2)
        hours2 = view.findViewById(R.id.hours2)
        minutes2 = view.findViewById(R.id.minutes2)
        linearLayout = view.findViewById(R.id.linearLayout)
        dayLinearLayout = view.findViewById(R.id.dayLinearLayout)
        commingsoon = view.findViewById(R.id.congo)
        val imageView22= view.findViewById<ImageView>(R.id.imageView2)

        fetchSystemDateTime()
        imageView22.setOnClickListener { loadFragment(notification()) }

    }
    fun setExploreClickListener(listener: ExploreClickListener) {
        exploreClickListener = listener
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
//                linearLayout.visibility = ViewGroup.GONE
//                dayLinearLayout.visibility = ViewGroup.GONE
                commingsoon.text = "The Wait is Over..."
                commingsoon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
                commingsoon.letterSpacing = 0f
                resetUI()
                isTimerRunning = false
            }
        }
        countDownTimer.start()
        isTimerRunning = true
    }
    private fun updateUI(countdownText: String) {
        val hourMinSec: List<String> = countdownText.split(":")
        hours1.text = (hourMinSec[1].toInt() / 10).toString()
        minutes1.text = (hourMinSec[2].toInt() / 10).toString()
        days1.text = (hourMinSec[0].toInt() / 10).toString()
        hours2.text = (hourMinSec[1].toInt() % 10).toString()
        minutes2.text = (hourMinSec[2].toInt() % 10).toString()
        days2.text = (hourMinSec[0].toInt() % 10).toString()
    }
    private fun resetUI() {
        days1.text = "0"
        hours1.text = "0"
        minutes1.text = "0"
        days2.text = "0"
        hours2.text = "0"
        minutes2.text = "0"
        isTimerRunning = false

    }

}