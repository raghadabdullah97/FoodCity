package com.example.foodcity.fragments.mainFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.foodcity.R
import com.example.foodcity.databinding.FragmentPrfileBinding

class ProfileFragment : Fragment(R.layout.fragment_prfile) {

    lateinit var binding: FragmentPrfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrfileBinding.bind(view)
    }



}