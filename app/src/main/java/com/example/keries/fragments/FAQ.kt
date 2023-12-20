package com.example.keries.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.keries.databinding.FragmentFAQ2Binding

class FAQ : Fragment() {
    private lateinit var binding: FragmentFAQ2Binding
    private val buttonStates = mutableMapOf<ImageView, Boolean>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFAQ2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonStates[binding.d1] = false
        buttonStates[binding.d2] = false
        buttonStates[binding.d3] = false
        buttonStates[binding.d4] = false
        buttonStates[binding.d5] = false
        buttonStates[binding.d6] = false

        binding.takmeBackaboutus.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        binding.l1.setOnClickListener {
            downFunction(binding.a1, binding.d1, binding.q1, binding.l1)
        }
        binding.l2.setOnClickListener {
            downFunction(binding.a2, binding.d2, binding.q2, binding.l2)
        }
        binding.l3.setOnClickListener {
            downFunction(binding.a3, binding.d3, binding.q3, binding.l3)
        }
        binding.l4.setOnClickListener {
            downFunction(binding.a4, binding.d4, binding.q4, binding.l4)
        }
        binding.l5.setOnClickListener {
            downFunction(binding.a5, binding.d5, binding.q5, binding.l5)
        }
        binding.l6.setOnClickListener {
            downFunction(binding.a6, binding.d6, binding.q6, binding.l6)
        }
    }

    private fun downFunction(a: TextView, d: ImageView, q: TextView, l: LinearLayout) {
        val currentState = buttonStates[d] ?: false
        if (currentState) {
            a.visibility = View.GONE
            a.setTextColor(Color.WHITE)
            q.setTextColor(Color.WHITE)
            d.animate().rotation(0F)
            d.setColorFilter(Color.WHITE)
            l.setBackgroundColor(Color.parseColor("#33ffffff"))

        } else {
            a.visibility = View.VISIBLE
            d.animate().rotation(180F)
            a.setTextColor(Color.WHITE)
            q.setTextColor(Color.WHITE)
            d.setColorFilter(Color.WHITE)
            l.setBackgroundColor(Color.BLACK)
        }
        // Update the button state
        buttonStates[d] = !currentState
    }
}