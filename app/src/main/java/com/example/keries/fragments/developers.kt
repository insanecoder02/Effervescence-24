package com.example.keries.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keries.databinding.FragmentDevelopersBinding
import kotlin.math.sqrt

class developers : Fragment() {
    private lateinit var binding: FragmentDevelopersBinding
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private val SHAKE_THRESHOLD = 800
    private var lastShakeTime: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevelopersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        binding.swiperefreshd.setOnRefreshListener {
                binding.swiperefreshd.isRefreshing = false
        }
    }

//    override fun onResume() {
//        super.onResume()
//        accelerometer?.let {
//            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
//        }
//    }
//    override fun onPause() {
//        super.onPause()
//        sensorManager.unregisterListener(this)
//    }

//    override fun onSensorChanged(event: SensorEvent?) {
//        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
//            val currentTime = System.currentTimeMillis()
//
//            if ((currentTime - lastShakeTime) > 1000) { // To avoid rapid multiple shakes
//                val x = event.values[0]
//                val y = event.values[1]
//                val z = event.values[2]
//
//                val acceleration = sqrt((x * x + y * y + z * z).toDouble())
//                if (acceleration > SHAKE_THRESHOLD) {
//                    playLottieAnimation()
//                    lastShakeTime = currentTime
//                }
//            }
//        }
//    }

    private fun playLottieAnimation() {
        binding.lottieAnimationView.visibility = View.VISIBLE
        binding.lottieAnimationView.playAnimation()
        val delayMillis = 3000L
        Handler(Looper.getMainLooper()).postDelayed({
            binding.lottieAnimationView.cancelAnimation()
            binding.lottieAnimationView.visibility = View.GONE
        }, delayMillis)
    }
}
