package com.testapp.whattowear.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView

@BindingAdapter(value = ["tripDate", "placeholderText"])
fun getProperTextForStartTextView(
    view: MaterialTextView,
    timestamp: Long,
    placeholderText: String
) {
    view.text = when (isDateConvertible(timestamp)) {
        true -> timestamp.convertDateToReadableFormat()
        false -> placeholderText
    }
}
