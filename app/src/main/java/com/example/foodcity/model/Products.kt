package com.example.foodcity.model

//import com.google.firebase.firestore.DocumentId
data class Products(
    val title: String = "",
    val details: String = "",
    val restaurantId: String = "",
    val imgUrl: String = "",
    val catType: ProductCategory ?=null,
)




