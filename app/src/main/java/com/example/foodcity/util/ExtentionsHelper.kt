package com.example.foodcity.util

import android.util.Patterns
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


fun Long.toTime(format: String = "hh:mma") =
    SimpleDateFormat(format, Locale.ENGLISH).format(this).toString()


private val TIME_PARSER = DateTimeFormatter.ofPattern("hh:mma", Locale.ENGLISH)

fun isRestaurantOpen(start: Long, end: Long):Boolean {
    val currentTime = LocalTime.parse(Date().time.toTime(), TIME_PARSER)
    val startTime = LocalTime.parse(start.toTime(), TIME_PARSER)
    val endTime = LocalTime.parse(end.toTime(), TIME_PARSER)
    return !(startTime.isAfter(currentTime) || endTime.isBefore(currentTime))
}