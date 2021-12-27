package com.example.foodcity.model

import com.google.firebase.firestore.DocumentId


data class ProductCategory(
    val title: String="",
    @DocumentId
    val id: String?=null,
)