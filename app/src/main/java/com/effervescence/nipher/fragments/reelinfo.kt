package com.effervescence.nipher.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.effervescence.nipher.R

class reelinfo : Fragment() {

    private lateinit var loadMe: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reelinfo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val reelNameTextView = view.findViewById<TextView>(R.id.reelNameTextview)
        loadMe = view.findViewById(R.id.loadMe)
        val videoView  = view.findViewById<VideoView>(R.id.videoView)
        val reel_Name = arguments?.getString("reelName")
        val reelId = arguments?.getString("reelID")
        val reelURl = arguments?.getString("reelURL")

        val tt = view.findViewById<ImageView>(R.id.takmeBackaboutus)
        tt.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        reelNameTextView.text = reel_Name
        loadMe.visibility = View.VISIBLE
        val videoUri = Uri.parse(reelURl)
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener { mp ->
            loadMe.visibility = View.GONE
            // Start the video playback
            mp.start()
        }
        videoView.requestFocus()
        videoView.start()

    }
}