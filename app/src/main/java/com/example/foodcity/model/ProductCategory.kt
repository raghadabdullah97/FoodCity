package com.example.foodcity.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductCategory(
    val title: String="",
):Parcelable{
    @DocumentId
    val id: String?=null

}