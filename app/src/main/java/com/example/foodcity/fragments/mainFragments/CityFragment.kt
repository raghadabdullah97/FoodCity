package com.example.foodcity.fragments.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.adapters.MyViewPagerAdapter
import com.example.foodcity.adapters.NearbyAdapter
import com.example.foodcity.databinding.FragmentCityBinding
import com.example.foodcity.databinding.FragmentPrfileBinding
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore

class CityFragment : Fragment(R.layout.fragment_city) {
    private val TAG = "CityFragment"
    lateinit var binding: FragmentCityBinding
    val args: CityFragmentArgs by navArgs()
    lateinit var viewModel: FirebaseViewModel
    lateinit var firebaseDb: FirebaseFirestore
    lateinit var tabTitle: Array<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityBinding.bind(view)
        //TabLayout:
        (requireActivity() as MainActivity).setToolbarTitle(args.cityName)
        firebaseDb = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
        //get from "File String " :
        tabTitle = arrayOf(
            getString(R.string.meals),
            getString(R.string.drinks),
            getString(R.string.sweets)
        )

        binding.apply {
        //from "MyViewPagerAdapter":
            viewPager.adapter = MyViewPagerAdapter(args.cityName, childFragmentManager, lifecycle)
            // Connects Tap with ViewPager :
            TabLayoutMediator(tabLayout, viewPager) { tab, postion ->
                //postion 0 = meals
                //postion 1 = drinks
                //postion 2 = sweets
                tab.text = tabTitle[postion]


            }.attach()
            //get text "restaurant inside ":
            tvNearby.text = "${getString(R.string.restaurant_inside_city)} ${args.cityName}"

        }
        fetchRestaurantsByCityName()

    }


    private fun fetchRestaurantsByCityName() {
        viewModel.fetchRestaurantsByCityName(firebaseDb, args.cityName)
            .observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.LOADING -> {

                        Log.e(TAG, "LOADING: ")

                    }
                    Status.SUCCESS -> {
                        it.data?.let { res ->
                            val adapter = NearbyAdapter(res)
                            binding.rvRestuarant.adapter = adapter
                            adapter.onItemClick = {
                                val action =
                                    CityFragmentDirections.actionCityFragmentToRestaurantDetailsFragment(
                                        it
                                    )
                                findNavController().navigate(action)
                            }
                        }

                        Log.e(TAG, "SUCCESS: ")
                    }
                    Status.ERROR -> {

                        Log.e(TAG, "ERROR: ")
                    }
                }
            })
    }


}


