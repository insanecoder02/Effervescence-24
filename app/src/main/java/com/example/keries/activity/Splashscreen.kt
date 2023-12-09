package com.example.keries.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.keries.R
import com.example.keries.activity.BaseHome
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.core.content.ContextCompat

class splashscreen : AppCompatActivity() {
    private val SPLASH_DELAY = 2000L // 2 seconds
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)




        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val logoview: ImageView = findViewById(R.id.logo)
        logoview.alpha = 0f

        // Fade in animation for the logo using ObjectAnimator
        val fadeInAnimator = ObjectAnimator.ofFloat(logoview, "alpha", 0f, 1f)
        fadeInAnimator.duration = 1000 // 1 second
        fadeInAnimator.interpolator = AccelerateDecelerateInterpolator()
        fadeInAnimator.start()

        // Initialize the MediaPlayer with the audio file
        mediaPlayer = MediaPlayer.create(this, R.raw.music)

        // Start playing the audio
        mediaPlayer?.start()

        // Using a Handler to delay the transition to the main activity
        Handler().postDelayed({
            val mainIntent = Intent(this, BaseHome::class.java)

            // Adding transition animation between activities
            startActivity(mainIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }


    private fun setStatusBarColor(colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, colorResId)
        }
    }
}
