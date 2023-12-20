package com.example.keries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keries.R
import com.example.keries.databinding.FragmentMoreBinding
import sponser

class More : Fragment() {
    private lateinit var binding: FragmentMoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.FAQ.setOnClickListener {
            loadFragment(FAQ())
        }
        binding.teamlayout.setOnClickListener {
            loadFragment(Team())
        }
        binding.SPONS.setOnClickListener {
            loadFragment(sponser())
        }
        binding.AboutUsss.setOnClickListener {
            loadFragment(about())
        }
        binding.Devs.setOnClickListener {
            loadFragment(developers())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }
}