package com.example.foodcity.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat


fun TextView.drawableStart(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
}

fun TextView.drawableEnd(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
}

fun TextView.textColor(textColor: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, textColor))
}

fun View.backgroundTint(color: Int) {
    this.backgroundTintList = ContextCompat.getColorStateList(this.context, color)
}

fun View.backgroundDrawable(drawable: Int) {
    this.background = ContextCompat.getDrawable(this.context, drawable)
    //   this.setPadding(10,0,10,0)
}

fun View.backgroundColor(color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(this.context, color))
}

fun TextView.colorLink(color: Int) {
    this.setLinkTextColor(ContextCompat.getColor(this.context, color))
}

fun TextView.alpha(alpha: Float) {
    this.alpha = alpha
}
//Change the color of the icon:
fun ImageView.tint(color: Int) {
    this.setColorFilter(
        ContextCompat.getColor(context, color),
        android.graphics.PorterDuff.Mode.SRC_IN
    );
}

//used in  RegisterFragment:
fun View.visible() {
    this.visibility = View.VISIBLE
}
//Hide BottomBar from "Register and sign in Fragment":
fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}
