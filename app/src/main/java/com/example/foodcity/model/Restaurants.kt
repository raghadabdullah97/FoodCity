package com.example.foodcity.model

import com.google.firebase.firestore.GeoPoint


data class Restaurants(
    val name: String="",
    val address: String="",
    val details: String="",
    val imgUrl: String="",
    val openTime: Long=0,
    val closeTime: Long=0,
    val location: GeoPoint?=null,

    )


























//@Parcelize
//data class Restaurants(
//    val name: String="",
//    val address: String="",
//    val details: String="",
//    val imgUrl: String="",
//    val openTime: Long=0,
//    val closeTime: Long=0,
//
//    @DocumentId
//    val id: String?=null,
//): Parcelable {
//    @IgnoredOnParcel
//    val location: GeoPoint?=null
//}