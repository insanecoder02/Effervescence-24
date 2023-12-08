package com.example.keries.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.keries.R
import com.example.keries.adapter.TeamAdapter
import com.example.keries.dataClass.TeamMember
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Team : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var Crv: RecyclerView
    private lateinit var frv: RecyclerView
    private lateinit var enmrv: RecyclerView
    private lateinit var crrv: RecyclerView
    private lateinit var prrv: RecyclerView
    private lateinit var blrv: RecyclerView
    private lateinit var hrv: RecyclerView
    private lateinit var mrv: RecyclerView
    private lateinit var flrv: RecyclerView
    private lateinit var crerv: RecyclerView
    private lateinit var trv: RecyclerView
    private lateinit var wrv: RecyclerView
    private lateinit var arv: RecyclerView
    private lateinit var orv: RecyclerView
    private lateinit var toolText : TextView
    private lateinit var logoTool : ImageView
    private lateinit var notifyTool : ImageView
//    private lateinit var loadingAnimationView: LottieAnimationView
    private  var  loadedRecyclerViewCount :Int = 1
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var shimCrv: ShimmerFrameLayout
    private lateinit var shimFrv: ShimmerFrameLayout
    private lateinit var shimEnmrv: ShimmerFrameLayout
    private lateinit var shimCrrv: ShimmerFrameLayout
    private lateinit var shimprrv: ShimmerFrameLayout
    private lateinit var shimblrv: ShimmerFrameLayout
    private lateinit var shimhrv: ShimmerFrameLayout
    private lateinit var shimmrv: ShimmerFrameLayout
    private lateinit var shimflrv: ShimmerFrameLayout
    private lateinit var shimcrerv: ShimmerFrameLayout
    private lateinit var shimtrv: ShimmerFrameLayout
    private lateinit var shimwrv: ShimmerFrameLayout
    private lateinit var shimarv: ShimmerFrameLayout
    private lateinit var shimorv: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_team, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Fresco.initialize(requireContext())
//        loadingAnimationView = view.findViewById(R.id.loadingAnimationView)

        Crv = view.findViewById(R.id.festiveCordi)
        frv = view.findViewById(R.id.financerelcyler)
        enmrv = view.findViewById(R.id.eventesmangereycl)
        crrv  = view.findViewById(R.id.coroprate)
        prrv = view.findViewById(R.id.pr)
        blrv  = view.findViewById(R.id.brnding)
        hrv = view.findViewById(R.id.hospitalllty)
        mrv = view.findViewById(R.id.mediaaaa)
        flrv = view.findViewById(R.id.flimingdevven)
        crerv = view.findViewById(R.id.creativesreyc)
        trv  = view.findViewById(R.id.tecnialreycler)
        wrv = view.findViewById(R.id.wed)
        arv = view.findViewById(R.id.apppp)
        orv = view.findViewById(R.id.cco)

        Crv.layoutManager = LinearLayoutManager(context)
        frv.layoutManager = LinearLayoutManager(context)
        enmrv.layoutManager = LinearLayoutManager(context)
        crrv.layoutManager = LinearLayoutManager(context)
        arv.layoutManager = LinearLayoutManager(context)
        trv.layoutManager = LinearLayoutManager(context)
        prrv.layoutManager = LinearLayoutManager(context)
        mrv.layoutManager = LinearLayoutManager(context)
        wrv.layoutManager = LinearLayoutManager(context)
        orv.layoutManager = LinearLayoutManager(context)
        flrv.layoutManager = LinearLayoutManager(context)
        blrv.layoutManager = LinearLayoutManager(context)
        crerv.layoutManager = LinearLayoutManager(context)
        hrv.layoutManager = LinearLayoutManager(context)

        fetchAndPopulateData("Coordinator", Crv)
        fetchAndPopulateData("FINANCE", frv)
        fetchAndPopulateData("EVENTS & MANAGEMENT", enmrv)
        fetchAndPopulateData("CORPORATE RELATIONS", crrv)
        fetchAndPopulateData("APP OPERATIONS", arv)
        fetchAndPopulateData("TECHNICAL", trv)
        fetchAndPopulateData("PUBLIC RELATIONS", prrv)
        fetchAndPopulateData("MEDIA", mrv)
        fetchAndPopulateData("WEB OPERATIONS", wrv)
        fetchAndPopulateData("OOC", orv)
        fetchAndPopulateData("FILMING", flrv)
        fetchAndPopulateData("BRANDING & LOGISTICS", blrv)
        fetchAndPopulateData("CREATIVES", crerv)
        fetchAndPopulateData("HOSPITALITY & TRAVEL", hrv)

        shimCrv = view.findViewById(R.id.shimmerCoordinator)
        shimFrv = view.findViewById(R.id.shimmerFinance)
        shimEnmrv = view.findViewById(R.id.shimmerEvents)
        shimCrrv  = view.findViewById(R.id.shimmerCorporate)
        shimprrv = view.findViewById(R.id.shimmerPublic)
        shimblrv  = view.findViewById(R.id.shimmerBranding)
        shimhrv = view.findViewById(R.id.shimmerHospitalitiy)
        shimmrv = view.findViewById(R.id.shimmerMedia)
        shimflrv = view.findViewById(R.id.shimmerFilming)
        shimcrerv = view.findViewById(R.id.shimmerCreatives)
        shimtrv  = view.findViewById(R.id.shimmerTechnical)
        shimwrv = view.findViewById(R.id.shimmerWeb)
        shimarv = view.findViewById(R.id.shimmerApp)
        shimorv = view.findViewById(R.id.shimmerOoc)

        shimmerEffect(shimCrv,Crv)
        shimmerEffect(shimFrv,frv)
        shimmerEffect(shimCrrv,crrv)
        shimmerEffect(shimEnmrv,enmrv)
        shimmerEffect(shimwrv,wrv)
        shimmerEffect(shimtrv,trv)
        shimmerEffect(shimorv,orv)
        shimmerEffect(shimhrv,hrv)
        shimmerEffect(shimblrv,blrv)
        shimmerEffect(shimprrv,prrv)
        shimmerEffect(shimmrv,mrv)
        shimmerEffect(shimflrv,flrv)
        shimmerEffect(shimcrerv,crerv)
        shimmerEffect(shimarv,arv)


        swipeRefreshLayout = view.findViewById(R.id.swiperefreshteam)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun shimmerEffect(shim:ShimmerFrameLayout , RV:RecyclerView) {
        RV.isNestedScrollingEnabled = false
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDirection(Shimmer.Direction.BOTTOM_TO_TOP)
            .setDuration(5000)
            .setAutoStart(true)
            .build()
        shim.setShimmer(shimmer)

        Handler(Looper.getMainLooper()).postDelayed({
            shim.stopShimmer()
            shim.isVisible = false
            RV.isVisible = true
            RV.isNestedScrollingEnabled = true
        },3000)
    }

    private fun fetchAndPopulateData(wing: String, recyclerView: RecyclerView) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val teamMemberList = fetchDataFromFirestore(wing)
                val teamAdapter = TeamAdapter(teamMemberList)
                recyclerView.adapter = teamAdapter
                loadedRecyclerViewCount++

                // Check if all RecyclerViews have loaded
//                if (loadedRecyclerViewCount == 14) {
//                    // All RecyclerViews have loaded, hide the loading animation
//                    loadingAnimationView.visibility = View.GONE
//                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun fetchDataFromFirestore(wing: String): List<TeamMember> = withContext(
        Dispatchers.IO) {
        try {
            val querySnapshot = db.collection("team")
                .whereEqualTo("wing", wing)
                .get()
                .await()

            val teamMemberList = mutableListOf<TeamMember>()
            for (document in querySnapshot) {
                val noString = document.get("no")?.toString() ?: ""
                val no = noString.toIntOrNull() ?: 0
                val name = document.getString("name") ?: ""
                val url = document.getString("url") ?: ""
                teamMemberList.add(TeamMember(name, wing, url, no))
            }
            return@withContext teamMemberList
        } catch (e: Exception) {
            throw e
        }
    }
}