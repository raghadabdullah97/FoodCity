package com.example.foodcity.fragments.mainFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.foodcity.R
import com.example.foodcity.databinding.FragmentFavoriteBinding
import com.example.foodcity.databinding.FragmentPrfileBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite){
    lateinit var binding: FragmentFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
    }
}