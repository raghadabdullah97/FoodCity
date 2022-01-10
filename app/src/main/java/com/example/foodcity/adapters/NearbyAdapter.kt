    package com.example.foodcity.adapters

    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.foodcity.R
    import com.example.foodcity.databinding.ItemHomeNearBinding
    import com.example.foodcity.model.Restaurants
    import com.example.foodcity.util.MySharedPref
    import com.example.foodcity.util.convertIntoKms
    import com.example.foodcity.util.distance


    class NearbyAdapter(var data: List<Restaurants>) :
    RecyclerView.Adapter<NearbyAdapter.ViewHolder>() {
    val TAG = "ChatAdapter"
    var onItemClick: ((Restaurants) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding =
            ItemHomeNearBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

       return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(data[i])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ViewHolder(private val binding: ItemHomeNearBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurants: Restaurants) {
            val context = binding.root.context
            binding.apply {
                tvName.text = restaurants.name


                val pref = MySharedPref(context)

                val distance = distance(
                    restaurants.location!!.latitude,
                    restaurants.location.longitude,
                    pref.getDouble("lat"),
                    pref.getDouble("lng")
                )
                //Item information on the card"Home Fragment"
                tvName.text = restaurants.name
                tvAddress.text = restaurants.address
                //"NearbyAdapter" = Calculate the distance to the List Restaurants:
                tvDistance.text = "${context.getString(R.string.nearby)} ${convertIntoKms(distance)}"
                Glide.with(context).load(restaurants.imgUrl).into(img)


                container.setOnClickListener {
                    onItemClick?.invoke(restaurants)
                }
            }


        }
    }


    }