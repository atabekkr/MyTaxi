package com.example.mapbox2.utils

import android.app.Activity
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
