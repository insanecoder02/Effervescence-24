package com.example.keries.fragments

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.keries.databinding.FragmentAboutBinding

class about : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.takmeBackaboutus.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        binding.imageView3.setOnClickListener {
            if (!binding.lottieAnimationView.isAnimating) {
                Toast.makeText(requireContext(), "#effefaadhoga", Toast.LENGTH_SHORT).show()
                binding.lottieAnimationView.visibility = View.VISIBLE
                binding.lottieAnimationView.playAnimation()
                val delayMillis = 3000L
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.lottieAnimationView.cancelAnimation()
                    binding.lottieAnimationView.visibility = View.GONE
                }, delayMillis)
            }
        }
        binding.instaImageView.setOnClickListener {
            openInstagramProfile()
        }
        binding.facebookImageView.setOnClickListener {
            openFacebookPage()
        }
        binding.gmailImageView.setOnClickListener {
            composeEmail()
        }
    }

    private fun openInstagramProfile() {
        val uri = Uri.parse("https://www.instagram.com/goeffervescence/")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.instagram.android")
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/goeffervescence/")
                )
            )
        }
    }

    private fun openFacebookPage() {
        val uri = Uri.parse("https://www.facebook.com/effervescence.iiita/")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.facebook.katana")
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/your_facebook_page/")
                )
            )
        }
    }

    private fun composeEmail() {
        val recipientEmail = "effervescence@iiita.ac.in"
        val subject = "Subject"
        val message = "Message body"
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$recipientEmail")
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Somehting Went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}