package com.example.foodcity.model

import com.google.firebase.firestore.GeoPoint


data class Cities(
    val name: String="",
    val location: GeoPoint?=null,

)