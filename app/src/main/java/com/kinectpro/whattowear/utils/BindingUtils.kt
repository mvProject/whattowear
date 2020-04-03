package com.kinectpro.whattowear.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.enums.ResourceStatus

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
 * @param dates selected dates which will be converted to single spanned string
 */
@BindingAdapter(value = ["conditionDates"])
fun getProperTextForWeatherConditionDatesTextView(
    view: MaterialTextView,
    dates: List<Long>
) {
    val backGroundColor = ContextCompat.getColor(view.context, R.color.colorAccent)
    val textColor = ContextCompat.getColor(view.context, R.color.colorPrimary)
    view.text = dates.convertToShortDateFormatSpannedString(backGroundColor,textColor)
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
 * Binding adapter for weather conditions name TextView's
 * @param view type of view which adapter can be binded
 * @param condition weather condition achieved from response
 */
@BindingAdapter(value = ["conditionRecommendation"])
fun getProperTextForWeatherRecommendationTextView(
    view: MaterialTextView,
    condition: String?
) {
    val recommendation = condition.convertIconToWeatherRecommendation()
    if (recommendation != null) {
        view.text = view.context.getString(recommendation)
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
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

/**
 * Binding adapter to weather state background image
 * @param view type of view which adapter can be binded
 * @param iconType drawable according state type
 */
@BindingAdapter(value = ["stateBackground"], requireAll = false)
fun loadImageBackground(
    view: ImageView, iconType: String?
) {
    if (iconType != null) {
        view.setImageResource(iconType.convertIconToDrawableBackground())
    }
}

/**
 * Binding adapter changing view visibility according to loading state
 * @param view type of view which adapter can be binded
 * @param state current state type
 */
@BindingAdapter(value = ["checkLoadingVisibility"])
fun setViewVisibilityAccordingLoadingStatus(
    view: View,
    state: ResourceStatus?
) {
    when (state) {
        ResourceStatus.LOADING -> view.visibility = View.VISIBLE
        else -> view.visibility = View.INVISIBLE
    }
}

/**
 * Binding adapter changing view visibility according to success data state
 * @param view type of view which adapter can be binded
 * @param state current state type
 */
@BindingAdapter(value = ["checkSuccessVisibility"])
fun setViewVisibilityAccordingSuccessStatus(
    view: View,
    state: ResourceStatus?
) {
    when (state) {
        ResourceStatus.SUCCESS -> view.visibility = View.VISIBLE
        else -> view.visibility = View.INVISIBLE
    }
}