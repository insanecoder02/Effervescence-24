package com.example.keries.fragments

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.keries.adapter.DevelopersAdapter
import com.example.keries.dataClass.DevelopersList
import com.example.keries.databinding.FragmentDevelopersBinding


class developers : Fragment(), DevelopersAdapter.OnItemClickListener {
    private lateinit var binding: FragmentDevelopersBinding
    private lateinit var developersList: MutableList<DevelopersList>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevelopersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.takmeBackaboutus.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        developersList = mutableListOf()

        developersList.add(
            DevelopersList(
                "PRAKRITI VASHISHTHA", "https://github.com/Prakriti-19", "Head"
            )
        )
        developersList.add(
            DevelopersList(
                "RIBHAV BANSAL", "https://github.com/RibhavBansal", "Head"
            )
        )
        developersList.add(
            DevelopersList(
                "BHUMIKA KALARU", "https://github.com/bhumika-kalaru", "Head"
            )
        )
        developersList.add(
            DevelopersList(
                "GAURAV CHHETRI", "https://github.com/muffinboy19", "App Wing Executive"
            )
        )
        developersList.add(
            DevelopersList(
                "ISHANT KUMAWAT", "https://github.com/insanecoder02", "App Wing Executive"
            )
        )
        developersList.add(
            DevelopersList(
                "PRANAV BANSAL", "https://github.com/PranavBansal21", "App Wing Executive"
            )
        )
        developersList.add(
            DevelopersList(
                "PRAKRUTI SHETTI", "https://github.com/praxyz19", "App Wing Executive"
            )
        )
        developersList.add(
            DevelopersList(
                "MOHD SALIK", "https://github.com/DIECINIUM", "App Wing Executive"
            )
        )
        developersList.add(
            DevelopersList(
                "AYUSHMAAN SONI", "https://github.com/AyushmanSoni", "Design Wing Executive"
            )
        )

        binding.developersRV.adapter = DevelopersAdapter(developersList, this)
        binding.developersRV.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onItemClick(item: DevelopersList) {


        if (item.name == "GAURAV CHHETRI") {
            Toast.makeText(
                requireContext(),
                " :) ",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.Github_id))
            startActivity(intent)
        }
        else {
            // For other developers, open their GitHub profile in a browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.Github_id))
            startActivity(intent)
        }



//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.Github_id))
//        startActivity(intent)
    }

//    private fun playLottieAnimation() {
//        binding.lottieAnimationView.visibility = View.VISIBLE
//        binding.lottieAnimationView.playAnimation()
//        val delayMillis = 3000L
//        Handler(Looper.getMainLooper()).postDelayed({
//            binding.lottieAnimationView.cancelAnimation()
//            binding.lottieAnimationView.visibility = View.GONE
//        }, delayMillis)
//    }
}
