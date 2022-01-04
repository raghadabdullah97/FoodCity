package com.example.foodcity.fragments
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.adapters.CitiesAdapter
import com.example.foodcity.adapters.NearbyAdapter
import com.example.foodcity.adapters.OffersAdapter
import com.example.foodcity.databinding.FragmentHomeBinding
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.FirebaseFirestore




class HomeFragment : Fragment(R.layout.fragment_home) {
    private val TAG = "HomeFragment"
    lateinit var binding: FragmentHomeBinding
    lateinit var firebaseDb: FirebaseFirestore
    lateinit var viewModel: FirebaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        (requireActivity()as MainActivity).setToolbarTitle(getString(R.string.home))
        firebaseDb = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
        fetchCities()
        fetchCNearby()
        initViewPager2Offers()

        binding.tvNearby.setOnClickListener {


            val action = HomeFragmentDirections.actionHomeFragmentToAllRestaurantsFragment()
            findNavController().navigate(action)
        }

    }


    private fun fetchCities() {
        viewModel.fetchCities(firebaseDb).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                    Log.e(TAG, "LOADING: ")

                }
                Status.SUCCESS -> {
                    it.data?.let { cities ->
                        binding.rvCities.adapter = CitiesAdapter(cities)

                    }

                    Log.e(TAG, "SUCCESS: ")
                }
                Status.ERROR -> {

                    Log.e(TAG, "ERROR: ")
                }
            }
        })
    }


    private fun fetchCNearby() {
        viewModel.fetchResNearby(requireContext(),firebaseDb).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                    Log.e(TAG, "LOADING: ")

                }
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        binding.rvRestuarant.adapter = NearbyAdapter(data)

                    }

                    Log.e(TAG, "SUCCESS: ")
                }
                Status.ERROR -> {

                    Log.e(TAG, "ERROR: ")
                }
            }
        })
    }


    private fun initViewPager2Offers() {
        viewModel.fetchOffers(firebaseDb).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                    Log.e(TAG, "LOADING: ")

                }
                Status.SUCCESS -> {
                    it.data?.let { offers ->
                        binding.pager.adapter = OffersAdapter(offers)
                        binding.indicator.setViewPager( binding.pager)

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






