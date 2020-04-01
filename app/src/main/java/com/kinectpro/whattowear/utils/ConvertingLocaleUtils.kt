package com.kinectpro.whattowear.utils

import android.content.Context
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.trip.TempSummary
import java.util.*

/**
 * Return temp summary converted to specified string template
 */
fun TempSummary.convertToReadableRange(context: Context): String {
    val currentMetricFormat = getProperMetricValue()
    return String.format(
        context.getString(R.string.temperature_value_description),
        maxValue.getProperMetricTempValue(),
        currentMetricFormat,
        minValue.getProperMetricTempValue(),
        currentMetricFormat
    )
}

/**
 * Extension obtaining unit according locale metric system
 * @return temperature unit value
 */
fun getProperMetricValue(): String {
    return when (Locale.getDefault().country) {
        LOCALE_COUNTRY_US -> METRIC_TYPE_FAHRENHEIT
        else -> METRIC_TYPE_CELSIUS
    }
}