package com.example.foodcity.model


data class Favorite (
    val userId:String="",
    // object of type Product :
    val product : Products ?= null

)