package com.kinectpro.whattowear.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
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
        true -> timestamp.convertDateToReadableFormat(DATE_READABLE_PATTERN)
        false -> placeholderText
    }
}

/**
 * Binding adapter for weather conditions appearance date TextView's
 * @param view type of view which adapter can be binded
 * @param dates selected dates which will be converted to single string
 */
@BindingAdapter(value = ["conditionDates"])
fun getProperTextForWeatherConditionDatesTextView(
    view: MaterialTextView,
    dates: List<Long>
) {
    view.text = dates.convertToShortDateFormatString()
}

/**
 * Binding adapter for weather conditions name TextView's
 * @param view type of view which adapter can be binded
 * @param condition weather condition achieved from response
 */
@BindingAdapter(value = ["conditionProperName"])
fun getProperTextForWeatherConditionTextView(
    view: MaterialTextView,
    condition: String?
) {
    if (condition != null) {
        view.text = view.context.getString(condition.convertIconToProperConditionName()!!)
    }
}

/**
 * Binding adapter to state icon
 * @param view type of view which adapter can be binded
 * @param iconType drawable according state type
 * @param placeHolder default drawable for view
 */
@BindingAdapter(value = ["stateDrawable", "placeholder"], requireAll = false)
fun loadImage(
    view: ImageView, iconType: String?, placeHolder: Drawable
) {
    if (iconType != null) {
        view.setImageResource(iconType.convertIconToDrawable())
    } else {
        view.setImageDrawable(placeHolder)
    }
}