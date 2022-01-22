package com.example.foodcity.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

//import com.google.firebase.firestore.DocumentId
//sending the package
@Parcelize
data class Products(
    val name: String = "",
    val details: String = "",
    val restaurantId: String = "",
    val imgUrl: String = "",
    val catType: ProductCategory? = null,
    @DocumentId
    val id: String?=null,
): Parcelable




