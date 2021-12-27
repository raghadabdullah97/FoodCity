package com.example.foodcity.model

import com.google.firebase.firestore.DocumentId

data class Offers(
    val imgUrl: String = "",
    @DocumentId
    val id: String?=null,
)