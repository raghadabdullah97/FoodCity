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
import com.example.foodcity.adapters.ProductsAdapter
import com.example.foodcity.databinding.FragmentCityBinding
import com.example.foodcity.databinding.FragmentPrfileBinding
import com.example.foodcity.databinding.FragmentProductsBinding
import com.example.foodcity.model.Products
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore

class ProductsFragment(val cityName: String, val position: Int) :
    Fragment(R.layout.fragment_products) {
    private val TAG = "ProductsFragment"
    lateinit var binding: FragmentProductsBinding
    val args: CityFragmentArgs by navArgs()
    lateinit var viewModel: FirebaseViewModel
    lateinit var firebaseDb: FirebaseFirestore
    lateinit var catType: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProductsBinding.bind(view)
        firebaseDb = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
        fetchProductsCityName()

        when (position){

            0-> catType = getString(R.string.meals)
            1-> catType = getString(R.string.drinks)
            2-> catType = getString(R.string.sweets)
            else -> catType = getString(R.string.meals)
        }
        fetchProductsCityName()

    }




        private fun fetchProductsCityName() {
        viewModel.fetchProductsCityName(firebaseDb, cityName, catType).observe(viewLifecycleOwner, {

            when (it.status) {
                Status.LOADING -> {
                    Log.e(TAG, "LOADING: ")

                }
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        initRecycleView(list)

                    }
                    Log.e(TAG, "SUCCESS: ")
                }
                Status.ERROR -> {

                    Log.e(TAG, "ERROR: ")
                }
            }
        })
    }



    private fun initRecycleView(data:List<Products>){
        val adapter = ProductsAdapter(data)
        binding.rvProducts.adapter = adapter

        adapter.onItemClick = {
            val action =
                CityFragmentDirections.actionCityFragmentToProductDetailsFragment(it)
            findNavController().navigate(action)
        }
//        adapter.onFavoriteClick={
//            Log.e(TAG, "onFavoriteClick: ", )
//            addToFavorite(it)
//        }
    }





    }



