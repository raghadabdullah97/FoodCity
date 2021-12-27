package com.example.foodcity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcity.databinding.ItemCityBinding
import com.example.foodcity.model.Cities
import com.example.foodcity.util.MySharedPref
import com.example.foodcity.util.convertIntoKms
import com.example.foodcity.util.distance

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


                val pref = MySharedPref(context)

                val distance =distance(
                    city.location!!.latitude,
                    city.location.longitude,
                    pref.getDouble("lat"),
                    pref.getDouble("lng")
                )

                tvDistance.text = convertIntoKms(distance)

                container.setOnClickListener {
                    onItemClick?.invoke(city)
                }
            }


        }
    }


}