package com.example.foodcity.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.adapters.ProductsAdapter

import com.example.foodcity.databinding.FragmentRestaurantDetailsBinding

import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.FirebaseFirestore


class RestaurantDetailsFragment : Fragment(R.layout.fragment_restaurant_details) {

    private val TAG = "RestaurantDetailsFragment "
    val arg: RestaurantDetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentRestaurantDetailsBinding
    lateinit var firebaseDb: FirebaseFirestore
    lateinit var viewModel: FirebaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRestaurantDetailsBinding.bind(view)
        (requireActivity()as MainActivity).setToolbarTitle(getString(R.string.restaurants))
        firebaseDb = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
        fetchProductsByRetName()


    }


    private fun fetchProductsByRetName(){
        viewModel.fetchProductsByRetName(firebaseDb, arg.docId).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                    Log.e(TAG, "LOADING: ")

                }
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        binding.rvProducts.adapter = ProductsAdapter(data)

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