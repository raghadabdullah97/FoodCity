package com.example.foodcity.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

fun ImageView.tint(color: Int) {
    this.setColorFilter(
        ContextCompat.getColor(context, color),
        android.graphics.PorterDuff.Mode.SRC_IN
    );
}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}


inline fun EditText.afterTextChanged(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }

    })


}

inline fun EditText.onTextChanged(crossinline listener: (String?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


        }

        override fun afterTextChanged(s: Editable?) {
            GlobalScope.launch(Dispatchers.Main) {
                delay(1500)
                if (!s.isNullOrBlank()) {
                    listener(s.toString())
                }
            }

        }

    })


}