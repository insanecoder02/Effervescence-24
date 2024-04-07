package com.effervescence.nipher.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.effervescence.nipher.databinding.FragmentEventslayoutBinding
import com.squareup.picasso.Picasso

class eventinfo : Fragment() {
    private lateinit var binding: FragmentEventslayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventslayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadimgEvent.visibility = View.VISIBLE

        val date = arguments?.getString("date")
        val details = arguments?.getString("details")
        val formLink = arguments?.getString("form")
        val name = arguments?.getString("name")
        val timee = arguments?.getString("time")
        val url = arguments?.getString("url")
        val venue = arguments?.getString("venue")
        val livevalue = arguments?.getString("live")
        val soco = arguments?.getString("soc")

        if (livevalue == "NO") {
            binding.liveButton.visibility = ViewGroup.GONE
        } else {
            binding.liveButton.visibility = ViewGroup.VISIBLE
        }
        binding.live_button?.setOnClickListener {
            if (!formLink.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formLink))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "The Link is Not Available", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.textView2.text = soco
        binding.descriptionEventTextView.text = details
        binding.textView.text = name
        binding.locationTextView.text = venue
        binding.TimeTextView.text = timee
        Picasso.get().load(url).into(binding.eventImage)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.eventImage.visibility = View.VISIBLE
            binding.loadimgEvent.visibility = View.GONE
        },3000)

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}