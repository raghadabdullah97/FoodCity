package com.example.foodcity.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foodcity.fragments.mainFragments.ProductsFragment

class MyViewPagerAdapter (val cityName:String, fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int { // number of fragment ( product,drink , sweet )
        return 3
    }

    override fun createFragment(position: Int): Fragment { // fragment فيها recycler View , display " product"
        return ProductsFragment(cityName , position)
    }


}
