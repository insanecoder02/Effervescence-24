package com.example.keries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.keries.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Schedule : Fragment() {
    private lateinit var spinner: Spinner
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("Keries") // Replace with your actual path

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_schedule, container, false)
        spinner = root.findViewById(R.id.spinner)
        setupSpinnerListener()
        fetchImageFromFirebase()
        return root

    }

    private fun setupSpinnerListener() {
        // Listener to get the spinner data from Firebase Realtime Database
        databaseReference.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val spinnerDataList = mutableListOf<String>()

                for (childSnapshot in dataSnapshot.children) {
                    // Assuming each child is a string value, modify accordingly if it's a complex object
                    val data = childSnapshot.getValue(String::class.java)
                    data?.let {
                        spinnerDataList.add(it)
                    }
                }

                // Create an ArrayAdapter and set it to the spinner
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    spinnerDataList
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })
    }


    private fun fetchImageFromFirebase() {
        // Listener to get the image URL from Firebase Realtime Database
        databaseReference.child("image_url").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val imageUrl = dataSnapshot.value.toString()

                // Use a library like Picasso or Glide to load and display the image
                // For example, using Picasso:
                // Picasso.get().load(imageUrl).into(logoTool)

                // Or using Glide:
                // Glide.with(requireContext()).load(imageUrl).into(logoTool)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })
    }
}