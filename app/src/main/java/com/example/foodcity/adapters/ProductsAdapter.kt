package com.example.foodcity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodcity.databinding.ItemOfferBinding
import com.example.foodcity.databinding.ItemProductBinding
import com.example.foodcity.model.Offers
import com.example.foodcity.model.Products
import com.example.foodcity.util.gone


class ProductsAdapter(var data: List<Products>,var isFavorite: Boolean=false) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {



    val TAG = "ProductsAdapter"

    var onItemClick: ((Products)->Unit)?=null
    var onFavoriteClick: ((Products) -> Unit)? = null



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(data[i])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Easy to access "item"xml :
        fun bind(products: Products) {
            val context = binding.root.context
            binding.apply {
                //"id "
                tvTitleProduct.text = products.name
                tvDetailsProduct.text = products.details
                tvCat.text = products.catType?.title
                Glide.with(context).load(products.imgUrl).into(carImgProduct)
        //When click on the "Product item" because To display product details:
                rlProduct.setOnClickListener {
                    onItemClick?.invoke(products)
                }
                btnAddToFavorite.setOnClickListener{
                    onFavoriteClick?.invoke(products)
                }

                if (isFavorite){
                    btnAddToFavorite.gone()
                }
            }


        }
    }


}