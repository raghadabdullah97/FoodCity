package com.example.foodcity.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurants(
    val name: String="",
    val address: String="",
    val details: String="",
    val imgUrl: String="",
    val openTime: Long=0,
    val closeTime: Long=0,

    @DocumentId
    val id: String?=null,
): Parcelable {
    @IgnoredOnParcel
    val location: GeoPoint?=null
}