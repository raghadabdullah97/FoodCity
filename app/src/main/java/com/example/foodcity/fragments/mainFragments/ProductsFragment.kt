package com.example.foodcity.fragments.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodcity.R
import com.example.foodcity.adapters.ProductsAdapter
import com.example.foodcity.databinding.FragmentProductsBinding
import com.example.foodcity.model.Products
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
//"tow constructor (val cityName: String, val position: Int) from "MyViewPagerAdapter ":
class ProductsFragment(val cityName: String, val position: Int) :
    Fragment(R.layout.fragment_products) {
    private val TAG = "ProductsFragment"
    lateinit var binding: FragmentProductsBinding
    val args: CityFragmentArgs by navArgs()
    lateinit var viewModel: FirebaseViewModel
    lateinit var firebaseDb: FirebaseFirestore
    lateinit var catType: String //position definition
    lateinit var pref: MySharedPref


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProductsBinding.bind(view)
        firebaseDb = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
        pref = MySharedPref(requireContext())

        fetchProductsCityName()
        //  check the position:
        when (position){

            0-> catType = getString(R.string.meals)
            1-> catType = getString(R.string.drinks)
            2-> catType = getString(R.string.sweets)
            else -> catType = getString(R.string.meals)
        }
        fetchProductsCityName()

    }


    //require from server:
    //used in "ProductFragment":
    //get The meal depends on the city, tow arg(cityName,catType):
        private fun fetchProductsCityName() {
        //data come from "viewModel":
        // "fetchProductsCityName"take 3 arg(firebaseDb,cityName,catType):
        viewModel.fetchProductsCityName(firebaseDb, cityName, catType).observe(viewLifecycleOwner, {

            when (it.status) {
                Status.LOADING -> {
                    Log.e(TAG, "LOADING: ")

                }
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        //display recycler :
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


    //When click on the "Product item" because To display product details:
    private fun initRecycleView(data:List<Products>){
        val adapter = ProductsAdapter(data, true)
        binding.rvProducts.adapter = adapter

        adapter.onItemClick = {
            val action =
                CityFragmentDirections.actionCityFragmentToProductDetailsFragment(it)
            findNavController().navigate(action)
        }
        //when click = add to fav:
        adapter.onFavoriteClick = {
            addToFavorite(it)
        }

    }
    //
    private fun addToFavorite(products: Products) {
    //pref.getString("userId")!!, products) from "MySharedPrefrance " from "sing in fragment ":
        viewModel.addToFavorite(firebaseDb, pref.getString("userId")!!, products)

    }


}