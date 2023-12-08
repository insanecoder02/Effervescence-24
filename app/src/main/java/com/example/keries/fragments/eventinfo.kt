package com.example.keries.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.keries.R
import com.squareup.picasso.Picasso

class eventinfo : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.event_layout, container, false)
        val register = root.findViewById<AppCompatButton>(R.id.register)
        val societyName = root.findViewById<TextView>(R.id.textView2)
        val eventName = root.findViewById<TextView>(R.id.textView)
        val eventDescription = root.findViewById<TextView>(R.id.descriptionEventTextView)
        val location = root.findViewById<TextView>(R.id.locationTextView)
        val time = root.findViewById<TextView>(R.id.TimeTextView)
        val image = root.findViewById<ImageView>(R.id.eventImage)
        val backButton = root.findViewById<ImageView>(R.id.backButton)

        val date = arguments?.getString("date")
        val details = arguments?.getString("details")
        val formLink = arguments?.getString("form")
        val name = arguments?.getString("name")
        val timee = arguments?.getString("time")
        val url = arguments?.getString("url")
        val venue = arguments?.getString("venue")
//        val no = arguments?.getString("no")


        Log.d("eventinfo", "Received data - Date: $date, Details: $details, Form: $formLink, Name: $name, Time: $timee, URL: $url, Venue: $venue ")

        register.setOnClickListener {
            // Check if the formLink is not null or empty
            if (!formLink.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formLink))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "The Link is Not Available", Toast.LENGTH_SHORT).show()
            }
        }


       // societyName.text = "Your Society Name"
        eventDescription.text = details
        eventName.text = name
        location.text=venue
        time.text=timee
        time.text=timee
        Picasso.get().load(url).into(image)

        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return root
    }


}