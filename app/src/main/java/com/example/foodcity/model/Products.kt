package com.example.foodcity.model

import com.google.firebase.firestore.DocumentId

//import com.google.firebase.firestore.DocumentId
data class Products(
    val name: String = "",
    val details: String = "",
    val restaurantId: String = "",
    val imgUrl: String = "",
    val catType: ProductCategory? = null,
    @DocumentId
    val id: String?=null,
)




