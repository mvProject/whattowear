package com.testapp.whattowear.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["tripDate", "placeholderText"])
fun getProperTextForStartTextView(view: TextView, timestamp: Long, placeholderTextId: Int) {
    view.text = when (isDateConvertible(timestamp)) {
        true -> timestamp.convertDateToReadableFormat()
        false -> view.resources.getString(placeholderTextId)
    }
}
