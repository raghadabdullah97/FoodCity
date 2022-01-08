package com.example.foodcity.fragments.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.adapters.MyViewPagerAdapter
import com.example.foodcity.adapters.ProductsAdapter
import com.example.foodcity.databinding.FragmentCityBinding
import com.example.foodcity.databinding.FragmentPrfileBinding
import com.example.foodcity.databinding.FragmentProductDetailsBinding
import com.example.foodcity.databinding.FragmentProductsBinding
import com.example.foodcity.model.Products
import com.example.foodcity.model.Restaurants
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    lateinit var binding: FragmentProductDetailsBinding
    val args: ProductDetailsFragmentArgs by navArgs()
    lateinit var viewModel: FirebaseViewModel
    lateinit var firestore: FirebaseFirestore
    lateinit var pref: MySharedPref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)
        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
        firestore = FirebaseFirestore.getInstance()
        pref= MySharedPref(requireContext())

        handleProductDetails()
        fetchRestaurantByProduct(args.product.restaurantId)

//        binding.btnFavorite.setOnClickListener {
//            Log.e("TAG", "btnFavorite: ", )
//            addToFavorite(args.product)
//        }
    }

    private fun handleProductDetails() {
        binding.apply {
            tvProductTitle.text = args.product.name
            tvProductDetails.text = args.product.details
            Glide.with(requireContext()).load(args.product.imgUrl).into(imgProduct)


        }
    }

    private fun fetchRestaurantByProduct(restaurantId: String) {
        viewModel.fetchRestaurantByProduct(firestore, restaurantId).observe(viewLifecycleOwner, {

            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    it.data?.let { rest ->

                        handleRestaurantData(rest)
                    }
                }
                Status.ERROR -> {

                }
            }

        })
    }

    private fun handleRestaurantData(restaurant: Restaurants) {
        binding.apply {
            tvRestaurantName.text = restaurant.name
            tvRestaurantDetails.text = restaurant.details
            Glide.with(requireContext()).load(restaurant.imgUrl).into(imgRestaurant)

            linStore.setOnClickListener {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToRestaurantDetailsFragment(
                        restaurant
                    )
                findNavController().navigate(action)
            }


        }
    }
//
//    private fun addToFavorite(products: Products){
//        viewModel.addToFavorite(Firebase.firestore,pref.getString("userId")!!,products)
//    }
}