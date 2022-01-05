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
import com.example.foodcity.util.*

import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import isRestaurantOpen
import toTime


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
        handleRestaurantItem()

    }


    private fun fetchProductsByRetName(){
        viewModel.fetchProductsByRetName(firebaseDb, arg.restaurant.id).observe(viewLifecycleOwner, {
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


    private fun handleRestaurantItem(){

        val pref = MySharedPref(requireContext())
        binding.itemRestaurant.apply {

            tvTitleStore.text = arg.restaurant.name
            //tvDistanceStore.text=  arg.restaurant.name

            val distance = distance(
                arg.restaurant.location!!.latitude,
                arg.restaurant.location!!.longitude,
                pref.getDouble("lat"),
                pref.getDouble("lng")
            )

            tvDistanceStore.text = convertIntoKms(distance)
            tvTimeStore.text="${arg.restaurant.openTime.toTime()} - ${arg.restaurant.closeTime.toTime()}"

            //A function that checks if the restaurant is open or closed
            //details from dataBase (time)
            if (isRestaurantOpen(arg.restaurant.openTime, arg.restaurant.closeTime)) {
                tvIsOpenStore.text = getString(R.string.open)
                tvIsOpenStore.backgroundTint(R.color.green_light)
                tvIsOpenStore.textColor(R.color.colorPrimary)
            } else {
                tvIsOpenStore.text = getString(R.string.close)
                tvIsOpenStore.backgroundTint(R.color.red_light)
                tvIsOpenStore.textColor(R.color.colorRed3)
            }

        }





    }









}