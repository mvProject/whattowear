package com.testapp.whattowear.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView

/**
 * Binding adapter for Date TextView's
 * @param view type of view which adapter can be binded
 * @param timestamp selected time which will be converted to specified date pattern
 * @param placeholderText default text for view
 */
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
