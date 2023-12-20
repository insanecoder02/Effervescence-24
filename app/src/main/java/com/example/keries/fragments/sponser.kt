import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.keries.R
import com.example.keries.adapter.SponsorAdapter
import com.example.keries.dataClass.sponserDataClass
import com.example.keries.databinding.FragmentShop2Binding
import com.example.keries.databinding.FragmentSponserBinding
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

class sponser : Fragment() {
    private lateinit var binding: FragmentSponserBinding
    private lateinit var sponseradapter: SponsorAdapter
    private var SponserList: MutableList<sponserDataClass> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSponserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sponseradapter = SponsorAdapter(SponserList)
        binding.sponserRecylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.sponserRecylerView.adapter = sponseradapter
        binding.sponserRecylerView.layoutManager =
            com.jackandphantom.carouselrecyclerview.CarouselLayoutManager(
                true, true, 0.5F, true, true, true, LinearLayoutManager.HORIZONTAL
            )
//        (binding.sponserRecylerView as CarouselRecyclerview).setInfinite(true)

//        binding.swiperefreshs.setOnRefreshListener {
//            fetchFirestoreData()
//        }
        binding.backsponser.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        fetchFirestoreData()
    }

    private fun fetchFirestoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("sponsors").get().addOnSuccessListener { documents ->
            // Clear existing data
            SponserList.clear()
            for (document in documents) {
                val name = document.getString("name") ?: ""
                val url = document.getString("url") ?: ""
                val desgination = document.getString("title") ?: ""
                val item = sponserDataClass(name, desgination, url)
                SponserList.add(item)
            }
            sponseradapter.notifyDataSetChanged()
//            binding.swiperefreshs.isRefreshing = false
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//            binding.swiperefreshs.isRefreshing = false
        }
    }
}