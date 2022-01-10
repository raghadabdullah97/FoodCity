package com.example.foodcity.adapters
import android.location.Location
import android.app.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcity.databinding.ItemCityBinding
import com.example.foodcity.model.Cities
import com.example.foodcity.util.LocationHelper
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.distance
import com.example.foodcity.util.distanceToKM
import java.util.*


class CitiesAdapter( var data: List<Cities>) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    val TAG = "ChatAdapter"
      var onItemClick: ((Cities) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding =
            ItemCityBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(data[i])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(city: Cities) {
            val context = binding.root.context
            binding.apply {
                tvName.text = city.name
                //get location user:
                val pref = MySharedPref(context)
            //"distance"The current location of the (user and city):
                val distance = distance(
                    city.location!!.latitude,
                    city.location.longitude,
                    pref.getDouble("lat"),
                    pref.getDouble("lng")
                ).distanceToKM()

                tvDistance.text = distance


            }
        }
    }


}