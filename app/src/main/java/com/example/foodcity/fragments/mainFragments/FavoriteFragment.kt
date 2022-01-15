package com.example.foodcity.fragments.mainFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodcity.MainActivity
import com.example.foodcity.R
import com.example.foodcity.adapters.ProductsAdapter
import com.example.foodcity.databinding.FragmentFavoriteBinding
import com.example.foodcity.fragments.RestaurantDetailsFragmentDirections
import com.example.foodcity.model.Products
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.Status
import com.example.foodcity.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: FirebaseViewModel
    lateinit var firestore: FirebaseFirestore
    lateinit var pref: MySharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.favorite))
        viewModel = ViewModelProvider(this)[FirebaseViewModel::class.java]
        firestore = FirebaseFirestore.getInstance()
        pref = MySharedPref(requireContext())
        fetchFavoriteProducts()
    }

    private fun fetchFavoriteProducts() {
        val products = ArrayList<Products>()
        //Comes with products belonging to the current user:
        viewModel.fetchFavoriteProducts(firestore, pref.getString("userId")!!)
            .observe(viewLifecycleOwner, {

                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        it.data?.let { data ->
                            data.forEach {
                                products.add(it.product!!)

                            }

                            initRecycleView(products)
                        }

                    }
                    Status.ERROR -> {

                    }
                }
            })
    }

    private fun initRecycleView(data: List<Products>) {
        //If "true", it will be hidden "add to fav":
        val adapter = ProductsAdapter(data,true)
        binding.rvFavorite.adapter = adapter

        adapter.onItemClick = {
            val action =
                FavoriteFragmentDirections.actionFavoriteFragmentToProductDetailsFragment(
                    it
                )
            findNavController().navigate(action)
        }
    }

}