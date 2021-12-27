package com.example.foodcity.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.adapters.NearbyAdapter
import com.example.foodcity.databinding.FragmentAllRestaurantsBinding
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.raghad.traditionalmeals.fragments.AllRestaurantsFragmentDirections

class AllRestaurantsFragment : Fragment(R.layout.fragment_all_restaurants) {
    private val TAG = "AllRestaurantsFragment"
    lateinit var binding: FragmentAllRestaurantsBinding
    lateinit var firebaseDb: FirebaseFirestore
    lateinit var viewModel: FirebaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllRestaurantsBinding.bind(view)
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.restaurants))

        firebaseDb = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
        fetchNearby()


    }

    private fun fetchNearby() {
        viewModel.fetchResNearby(requireContext(), firebaseDb).observe(viewLifecycleOwner, {
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
                                AllRestaurantsFragmentDirections.actionAllRestaurantsFragmentToRestaurantDetailsFragment(
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