package com.example.foodcity.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.adapters.NearbyAdapter
import com.example.foodcity.databinding.FragmentAllRestaurantsBinding
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.FirebaseFirestore


class AllRestaurantsFragment : Fragment(R.layout.fragment_all_restaurants) {
private val TAG = "AllRestaurantsFragment"
lateinit var binding: FragmentAllRestaurantsBinding
lateinit var firebaseDb: FirebaseFirestore
lateinit var viewModel: FirebaseViewModel

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
   super.onViewCreated(view, savedInstanceState)
   binding = FragmentAllRestaurantsBinding.bind(view)
    (requireActivity()as MainActivity).setToolbarTitle(getString(R.string.restaurants))
    firebaseDb = FirebaseFirestore.getInstance()
   viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
   fetchCNearby()


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



}

