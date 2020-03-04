package com.kinectpro.whattowear.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso


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

/**
 * Binding adapter to state icon
 * @param view type of view which adapter can be binded
 * @param url drawable according state type
 * @param placeHolder default drawable for view
 */
@BindingAdapter(value = ["stateDrawable", "placeholder"], requireAll = false)
fun loadImage(
    view: ImageView, url: String?, placeHolder: Drawable
) {
    Picasso.get().load(url).placeholder(placeHolder).into(view)
}
