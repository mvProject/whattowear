package com.testapp.whattowear.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("dialogVisible")
fun setViewVisibility(view: View, mode: Boolean) {
    if (mode)
        view.visibility = View.VISIBLE
    else {
        view.visibility = View.GONE
    }
}