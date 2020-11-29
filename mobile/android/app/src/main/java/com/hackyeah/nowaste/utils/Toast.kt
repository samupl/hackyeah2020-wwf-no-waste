package com.hackyeah.nowaste.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(resId: Int) =
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

fun Context.showToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()