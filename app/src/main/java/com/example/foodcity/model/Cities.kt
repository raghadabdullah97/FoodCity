package com.example.foodcity.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint

//data from firebase "tubule"
data class Cities(
    val name: String="",
    //"GeoPoint" attach = firebase
    val location: GeoPoint?=null,
    //anuteted from firebase:
    @DocumentId
    val id: String?=null,

)

