package com.example.foodcity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodcity.databinding.ItemCityBinding
import com.example.foodcity.databinding.ItemOfferBinding
import com.example.foodcity.model.Cities
import com.example.foodcity.model.Offers
import com.example.foodcity.util.MySharedPref


class OffersAdapter(var data: List<Offers>) :
    RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    val TAG = "OffersAdapter"

    var onItemClick: ((String)->Unit)?=null


        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
            val binding =
                ItemOfferBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

            return ViewHolder(binding)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
            viewHolder.bind(data[i])
        }

        override fun getItemCount(): Int {
            return data.size
        }


        inner class ViewHolder(private val binding: ItemOfferBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(Offers: Offers) {
                val context = binding.root.context
                binding.apply {
                Glide.with(context).load(Offers. imgUrl).into(img)











                }
            }
        }


}







































