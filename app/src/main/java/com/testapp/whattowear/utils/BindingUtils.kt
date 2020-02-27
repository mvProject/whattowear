package com.testapp.whattowear.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.testapp.whattowear.R


@BindingAdapter("viewEndTripText")
fun getProperTextForEndTextView(view: TextView, value: Long) {
    view.text =
        if (value > 0) value.getDateToReadableFormat(view.context) else view.resources.getString(R.string.btn_end_date_name)
}

@BindingAdapter("viewStartTripText")
fun getProperTextForStartTextView(view: TextView, value: Long) {
    view.text =
        if (value > 0) value.getDateToReadableFormat(view.context) else view.resources.getString(R.string.btn_start_date_name)
}
