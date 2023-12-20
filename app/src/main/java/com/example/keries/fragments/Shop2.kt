package com.example.keries.fragments

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
import com.example.keries.databinding.FragmentShop2Binding
import com.squareup.picasso.Picasso

class Shop2 : Fragment() {
    private lateinit var binding: FragmentShop2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShop2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadimgEvent.visibility = View.VISIBLE

        val prize = arguments?.getString("prize")
        val name = arguments?.getString("name")
        val type = arguments?.getString("type")
        val descrip = arguments?.getString("descrip")
        val image = arguments?.getString("image")
        val formLink = arguments?.getString("form")

        binding.buyMerch.setOnClickListener {
            if (!formLink.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formLink))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Error while Loading link", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.backBut.setOnClickListener { fragmentManager?.popBackStack() }
        binding.textView1.text = name
        binding.textView2.text = type
        binding.textView3.text = prize
        binding.productDescription.text = descrip
        Picasso.get().load(image).into(binding.shopImage)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.shopImage.visibility = View.VISIBLE
            binding.loadimgEvent.visibility = View.GONE
        },3000)
    }
}