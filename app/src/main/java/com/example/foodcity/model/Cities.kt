package com.example.foodcity.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint


data class Cities(
    val name: String="",
    val location: GeoPoint?=null,
    @DocumentId
    val id: String?=null,
)