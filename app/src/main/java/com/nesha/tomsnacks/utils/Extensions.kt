package com.nesha.tomsnacks.utils

import android.app.Activity
import android.view.View
import android.widget.Toast
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Int.currencyFormat(): String {
    val currency = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(this)
    return currency
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}