package com.effervescence.nipher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.effervescence.nipher.adapter.NotificationAdapter
import com.effervescence.nipher.dataClass.NotificationModel
import com.effervescence.nipher.databinding.FragmentNotificationBinding
import com.google.firebase.firestore.FirebaseFirestore

class notification : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter
    private var NotificatonList: MutableList<NotificationModel> = mutableListOf()
    private var db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.takmeBacknotfiy.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        notificationAdapter = NotificationAdapter(NotificatonList)
        binding.notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                reverseLayout = true
                stackFromEnd = true
            }
        binding.notificationRecyclerView.adapter = notificationAdapter
        fetchFirestoreData()
    }

    private fun fetchFirestoreData() {
        db.collection("Notification").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val info = document.getString("info") ?: ""
                        val image = document.getString("c")?:""
                        val time = document.getString("time")?:""
                val item = NotificationModel(info,time)
                NotificatonList.add(item)
            }
            notificationAdapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}