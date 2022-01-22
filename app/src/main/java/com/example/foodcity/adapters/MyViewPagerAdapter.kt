package com.example.foodcity.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foodcity.fragments.mainFragments.ProductsFragment


//second type of " Adapter " :
//add pram "val cityName:String":
class MyViewPagerAdapter (val cityName:String, fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    // this fun " number" of fragment ( product,drink , sweet ):
    override fun getItemCount(): Int {
        return 3
    }
    // fragment فيها recycler View , display " product":
    override fun createFragment(position: Int): Fragment {
        //add pram "val cityName:String":
        return ProductsFragment(cityName , position)
    }


}
