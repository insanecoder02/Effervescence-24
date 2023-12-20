package com.example.keries.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.keries.R
import com.example.keries.adapter.productAdapter
import com.example.keries.dataClass.productDataClass
import com.example.keries.databinding.FragmentShopBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.Shimmer.Direction.BOTTOM_TO_TOP
import com.google.firebase.firestore.FirebaseFirestore

class Shop : Fragment(), productAdapter.OnItemClickListener {
    private lateinit var binding: FragmentShopBinding
    private lateinit var productadapter: productAdapter
    private var productList: MutableList<productDataClass> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shimmer = Shimmer.AlphaHighlightBuilder().setDirection(BOTTOM_TO_TOP).setDuration(5000)
            .setAutoStart(true).build()
        binding.shimmer.setShimmer(shimmer)

        productadapter = productAdapter(productList, this)
        binding.productreyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.productreyclerview.adapter = productadapter

//        binding.swiperefreshshop.setOnRefreshListener {
//            fetchFirestoreData()
//        }
        if (savedInstanceState == null) {
            fetchFirestoreData()
        }
    }

    override fun onItemClick(item: productDataClass) {
        val bundle = Bundle()
        bundle.putString("prize", item.productPrize)
        bundle.putString("name", item.productNames)
        bundle.putString("type", item.productTypes)
        bundle.putString("descrip", item.productDescription)
        bundle.putString("image", item.productImageUrl)
        bundle.putString("form", item.productForm)

        val shop2Fragment = Shop2()
        shop2Fragment.arguments = bundle

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, shop2Fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun fetchFirestoreData() {
        productList.clear()
        val db = FirebaseFirestore.getInstance()
        db.collection("Merch").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val name = document.getString("name") ?: ""
                val type = document.getString("type") ?: ""
                val description = document.getString("desc") ?: ""
                val prize = document.getString("cost") ?: ""
                val url = document.getString("url") ?: ""
                val form = document.getString("form") ?: ""
                val item = productDataClass(name, type, description, prize, url, form)
                productList.add(item)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.shimmer.stopShimmer()
                    binding.shimmer.isVisible = false
                    binding.productreyclerview.isVisible = true
                }, 3000)

            }
            productadapter.notifyDataSetChanged()

//            binding.swiperefreshshop.isRefreshing = false
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//            binding.swiperefreshshop.isRefreshing = false
        }
    }
}
