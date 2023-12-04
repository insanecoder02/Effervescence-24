package com.example.keries.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.example.keries.R
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class developers : Fragment() {
    private lateinit var toolText: TextView
    private lateinit var logoTool: ImageView
    private lateinit var notifyTool: ImageView
    private lateinit var lottieDev: LottieAnimationView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_developers, container, false)

        // Initialize views
        lottieDev = root.findViewById(R.id.lottieAnimationView)
        swipeRefreshLayout = root.findViewById(R.id.swiperefreshd)

        // Set up the refresh listener
        swipeRefreshLayout.setOnRefreshListener {
            // Handle the refresh action here (e.g., load new data)
            // You can place the logic for refreshing your content here

            // For demonstration purposes, let's simulate a delay with a Handler
            Handler(Looper.getMainLooper()).postDelayed({
                // Stop the refresh animation
                swipeRefreshLayout.isRefreshing = false

                // Play the Lottie animation after refresh
                playLottieAnimation()
            }, 2000L)
        }

        // Play the Lottie animation when the fragment is initially opened
        playLottieAnimation()

        return root
    }

    private fun playLottieAnimation() {
        lottieDev.visibility = View.VISIBLE
        lottieDev.playAnimation()

        // Delay for a specified duration (e.g., 3000 milliseconds or 3 seconds)
        val delayMillis = 3000L
        Handler(Looper.getMainLooper()).postDelayed({
            // Stop the animation after the delay
            lottieDev.cancelAnimation()
            lottieDev.visibility = View.GONE
        }, delayMillis)
    }
}
