package com.kinectpro.whattowear.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.kinectpro.whattowear.data.model.trip.TempSummary
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.data.model.trip.WeatherCondition
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
        true -> timestamp.convertDateToReadableFormat(DATE_READABLE_PATTERN)
        false -> placeholderText
    }
}

@BindingAdapter(value = ["conditionDates"])
fun getProperTextForWeatherConditionDatesTextView(
    view: MaterialTextView,
    dates: List<Long>
) {
    view.text = dates.convertToShortDateFormatString()
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

fun GetDummy(): TripModel {
    return TripModel(
        TempSummary(5f, 10f),
        TempSummary(-3f, -1f),
        listOf(
            WeatherCondition("rain", getTestDateList()),
            WeatherCondition("sun", getTestDateList())
        )
    )
}

private fun getTestDateList(): List<Long> {
    return listOf(1583704800, 1583791200, 1583877600, 1583964000, 1584050400)
}
