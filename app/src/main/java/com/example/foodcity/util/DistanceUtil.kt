package com.example.foodcity.util

import java.lang.Math.*
//this class Calculates the distance between two points"":
fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val theta = lon1 - lon2
    var dist = (sin(deg2rad(lat1))
            * sin(deg2rad(lat2))
            + (cos(deg2rad(lat1))
            * cos(deg2rad(lat2))
            * cos(deg2rad(theta))))
    dist = acos(dist)
    dist = rad2deg(dist)
    dist *= 60 * 1.1515
    return dist
}

private fun deg2rad(deg: Double): Double {
    return deg * Math.PI / 180.0
}

private fun rad2deg(rad: Double): Double {
    return rad * 180.0 / Math.PI
}
//"distance"The current location of the (user and city):
fun Double.distanceToKM():String{
    return   String.format("%.2f", this / 1000) + "km"
}

fun convertIntoMiles(km: Double): String {
    return  String.format("%.2f", km / 1.609) + "miles"

}
//"NearbyAdapter" = Calculate the distance to the List Restaurants:
fun convertIntoKms(miles: Double): String {
    return   String.format("%.2f", 1.609 * miles) + "km"
}