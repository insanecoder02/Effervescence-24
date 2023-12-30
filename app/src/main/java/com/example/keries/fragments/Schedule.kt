package com.example.keries.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.keries.R
import com.example.keries.databinding.FragmentScheduleBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Schedule : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val imageUrlMap = mutableMapOf<String, String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loadschedule.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            binding.scheduleShowImageView.visibility = View.VISIBLE
            binding.loadschedule.visibility = View.GONE
        }, 3000)
        fetchDataForSpinner(requireContext())
        setSpinnerListener()
        setDownloadClickListener()
    }

    private fun setDownloadClickListener() {
        binding.downlaod.setOnClickListener {
            val drawable = binding.scheduleShowImageView.drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                saveBitmapToStorage(requireContext(), bitmap)
            }
        }
    }

    private fun saveBitmapToStorage(alpha: Context, bitmap: Bitmap) {
        val filename = "image.png"
        val file = File(alpha.getExternalFilesDir(null), filename)
        Toast.makeText(
            alpha, "Trying to Download the Image.....", Toast.LENGTH_SHORT
        ).show()
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            Toast.makeText(
                alpha, "Image downloaded successfully", Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                alpha, "Error downloading image", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setSpinnerListener() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long
            ) {
                val selectedItemName = parentView.getItemAtPosition(position).toString()
                val imageUrl = imageUrlMap[selectedItemName]
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.whilte_broder)
                        .error(R.drawable.effesvghome)
                        .into(binding.scheduleShowImageView)

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                binding.scheduleShowImageView.setImageResource(R.drawable.effesvghome)
            }
        }
    }

    private fun fetchDataForSpinner(alpha: Context) {
        firestore.collection("schedule").orderBy("no").get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                querySnapshot?.let {
                    val spinnerDataList = mutableListOf<String>()
                    for (document in it.documents) {
                        val itemName = document.getString("name")
                        val imageUrl = document.getString("url")
                        itemName?.let {
                            spinnerDataList.add(it)
                            imageUrl?.let { url ->
                                imageUrlMap[it] = url
                            }
                        }
                    }
                    val adapter = ArrayAdapter(
                        alpha, android.R.layout.simple_spinner_item, spinnerDataList
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinner.adapter = adapter
                    Log.d("ImageUrlMap", imageUrlMap.toString())
                }
            }.addOnFailureListener { exception ->
                Log.e("FetchDataError", "Error fetching data: ${exception.message}")
            }
    }
}