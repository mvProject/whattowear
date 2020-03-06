package com.kinectpro.whattowear.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.kinectpro.whattowear.data.model.trip.TempSummary
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.data.model.trip.WeatherCondition

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
 * Binding adapter to state icon
 * @param view type of view which adapter can be binded
 * @param url drawable according state type
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

// Dummy Data
fun GetDummy(): TripModel {
    return TripModel(
        TempSummary(5f, 10f),
        TempSummary(-3f, -1f),
        listOf(
            WeatherCondition(
                "clear-day",
                listOf(1583704800, 1583791200, 1583877600, 1583964000, 1584050400)
            ),
            WeatherCondition("clear-night", listOf(1583704800, 1583791200, 1583877600, 1583964000)),
            WeatherCondition("rain", listOf(1583704800, 1583791200, 1583877600)),
            WeatherCondition("snow", listOf(1583704800, 1583791200)),
            WeatherCondition("sleet", listOf(1583704800)),
            WeatherCondition("wind", listOf(1583791200)),
            WeatherCondition("fog", listOf(1583877600)),
            WeatherCondition("cloudy", listOf(1583964000)),
            WeatherCondition("partly-cloudy-day", listOf(1583964000, 1584050400)),
            WeatherCondition("partly-cloudy-night", listOf(1584050400))
        )
    )
}
