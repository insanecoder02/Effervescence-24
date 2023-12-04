import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.keries.R
import com.example.keries.fragments.eventinfo
import com.example.keries.fragments.eventreel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class basefragmentevent : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basefragmentevent, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        // Set up ViewPager with the adapter

        viewPager.adapter =
            BaseFragmentPagerAdapter(requireActivity().supportFragmentManager, lifecycle, null)
        // Set up TabLayout with ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Customize tab titles if needed
            when (position) {
                0 -> tab.text = "Fragment 1"
                1 -> tab.text = "Fragment 2"
            }
        }.attach()

        return view
    }


    private class BaseFragmentPagerAdapter(
        fragmentActivity: FragmentManager, lifecycle: Lifecycle, private val bundleData: Bundle?
    ) : FragmentStateAdapter(fragmentActivity , lifecycle) {
        override fun getItemCount(): Int = 2 // Number of tabs
        private var eventinfoFragment: eventinfo? = null

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    eventinfoFragment = eventinfo()
                    eventinfoFragment?.arguments = bundleData
                    eventinfoFragment!!
                } // Your first fragment
                1 -> eventreel().apply { arguments = bundleData } // Your second fragment
                else -> throw IllegalArgumentException("Invalid position")
            }
        }

    }
}
