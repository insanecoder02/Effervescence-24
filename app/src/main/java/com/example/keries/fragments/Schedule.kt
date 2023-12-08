package com.example.keries.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
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
    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val imageUrlMap = mutableMapOf<String, String>()
    private lateinit var downlaodme: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        spinner = binding.root.findViewById(R.id.spinner)
        downlaodme = binding.root.findViewById(R.id.downlaod)
        imageView=binding.root.findViewById(R.id.scheduleShowImageView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchDataForSpinner()
        setSpinnerListener()
        setDownloadClickListener()
    }

    private fun setDownloadClickListener() {
        downlaodme.setOnClickListener {
            // Download the image currently displayed in the imageView
            val drawable = imageView.drawable
            // Check if the drawable is a BitmapDrawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                // Implement logic to save the bitmap to the phone storage
                saveBitmapToStorage(bitmap)



            }
        }
    }

    private fun saveBitmapToStorage(bitmap: Bitmap) {
        val filename = "image.png"
        val file = File(requireContext().getExternalFilesDir(null), filename)

        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            Toast.makeText(
                requireContext(), "Image downloaded successfully", Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                requireContext(), "Error downloading image", Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun setSpinnerListener() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long
            ) {
                // Get the selected item name
                val selectedItemName = parentView.getItemAtPosition(position).toString()

                // Use Picasso to load the image into the imageView based on the selected item name
                val imageUrl = imageUrlMap[selectedItemName]
                if (imageUrl.isNullOrBlank()) {
                    // If the URL is empty or null, you can set the imageView to display nothing
                    // or set it to a default image
                    imageView.setImageResource(R.drawable.effesvghome)
                } else {
                    Picasso.get().load(imageUrl).into(imageView)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // If the URL is empty or null, you can set the imageView to display nothing
                // or set it to a default image
                imageView.setImageResource(R.drawable.effesvghome)  // Set to nothing (empty)
                // Alternatively, set to a default image
                // imageView.setImageResource(R.drawable.default_image)
            }
        }
    }

    private fun fetchDataForSpinner() {
        firestore.collection("schedule").get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                querySnapshot?.let {
                    val spinnerDataList = mutableListOf<String>()
                    for (document in it.documents) {
                        // Assuming each document has fields "name" and "url"
                        val itemName = document.getString("name")
                        val imageUrl = document.getString("url")

                        itemName?.let {
                            spinnerDataList.add(it)
                            imageUrl?.let { url ->
                                imageUrlMap[it] = url
                            }
                        }
                    }

                    // Create an ArrayAdapter and set it to the spinner
                    val adapter = ArrayAdapter(
                        requireContext(), android.R.layout.simple_spinner_item, spinnerDataList
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                    Log.d("ImageUrlMap", imageUrlMap.toString())
                }
            }.addOnFailureListener { exception ->
                Log.e("FetchDataError", "Error fetching data: ${exception.message}")
            }
    }
}