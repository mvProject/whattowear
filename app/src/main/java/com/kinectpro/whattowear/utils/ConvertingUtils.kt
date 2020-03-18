package com.kinectpro.whattowear.utils

import android.content.Context
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.StyleSpan
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.response.DarkSkyWeather
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.model.trip.TempSummary
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

/**
 * Specified pattern to convert long variable timestamp
 */
const val STATE_DATE_READABLE_PATTERN = "dd.MM"
const val DATE_READABLE_PATTERN = "dd/MM/yy"
const val DEFAULT_WEATHER_STATE = "defaultWeatherState"

/**
 *
 */
const val BACKGROUND_CORNER_RADIUS = 20
const val HORIZONTAL_PADDING = 15
const val VERTICAL_PADDING = 1
/**
 * Extension Method to response data class which
 * try to extract only specified non-null fields
 * @return instance of WeatherData data class or null
 */
fun DarkSkyWeather.convertToWeatherDataModel(): WeatherData? {
    if ((this.daily.data.first().sunsetTime != null) and (this.daily.data.first().apparentTemperatureHigh != null) and (this.daily.data.first().apparentTemperatureLow != null)) {
        return WeatherData(
            this.daily.data.first().sunsetTime!!,
            this.daily.data.first().apparentTemperatureHigh!!,
            this.daily.data.first().apparentTemperatureLow!!,
            this.daily.data.first().icon
        )
    }
    return null
}

/**
 * Extension Method to non-null long variable which
 * convert value to specified date pattern
 * @return String converted date value
 */
fun Long.convertDateToReadableFormat(pattern: String): String {
    return SimpleDateFormat(
        pattern,
        Locale.getDefault()
    ).format(this)
}

/**
 * Method checks can value be converted
 * @param date timestamp value which will be convert
 * @return true if date is convertible otherwise false
 */
fun isDateConvertible(date: Long?): Boolean {
    if (date != null)
        if (date > 0)
            return true
    return false
}

/**
 * Extension filtering day temperatures from weather forecast
 * @return list of day temp from weather forecast list
 */
fun List<WeatherData>.getDayTemperatureAsList(): List<Float> {
    val tempResultList = mutableListOf<Float>()
    for (temp in this) {
        if (temp.temperatureDay != null) {
            tempResultList.add(temp.temperatureDay)
        }
    }
    return tempResultList
}

/**
 * Extension filtering night temperatures from weather forecast
 * @return list of day temp from weather forecast list
 */
fun List<WeatherData>.getNightTemperatureAsList(): List<Float> {
    val tempResultList = mutableListOf<Float>()
    for (temp in this) {
        if (temp.temperatureNight != null) {
            tempResultList.add(temp.temperatureNight)
        }
    }
    return tempResultList
}

/**
 * Extension filtering weather states from weather forecast
 * @return list of weather states in range without nulls and duplicates
 */
fun List<WeatherData>.getWeatherStatesUniqueAsList(): List<String> {
    val tempResultList = mutableListOf<String>()
    for (temp in this) {
        temp.weatherState?.let {
            if (it.checkIconIsWeatherCondition()) {
                tempResultList.add(it)
            } else {
                tempResultList.add(DEFAULT_WEATHER_STATE)
            }
        }
    }
    return tempResultList.distinct()
}

/**
 * Extension to convert list of dates to specified short date united spanned string
 * @return single spanned string value
 */
fun List<Long>.convertToShortDateFormatSpannedString(backgroundColor : Int,foregroundColor : Int): SpannedString {
    return this.joinToSpannedString(" ") {
        TimeUnit.SECONDS.toMillis(it).convertDateToReadableFormat(
            STATE_DATE_READABLE_PATTERN
        ).convertToRoundedBackgroundSpannable(backgroundColor,foregroundColor)
    }
}

/**
 * Extension for joining spanned strings
 * @return SpannableStringBuilder
 */
private fun <T> Iterable<T>.joinToSpannedString(separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...", transform: ((T) -> CharSequence)? = null): SpannedString {
    return SpannedString(joinTo(SpannableStringBuilder(), separator, prefix, postfix, limit, truncated, transform))
}

/**
 * Extension convert temp summary to specified string
 * @return single string value
 */
fun TempSummary.convertToReadableRange(context : Context): String {
    return String.format(context.getString(R.string.temperature_max_value_description),maxValue.roundToInt()) + "\n" + String.format(context.getString(R.string.temperature_min_value_description),minValue.roundToInt())
}

/**
 * Extension converting any single string to spannable with predefined decoration
 */
fun String.convertToRoundedBackgroundSpannable(backgroundColor : Int,foregroundColor : Int) : SpannableString{
    val span = SpannableString("$this ")
    span.setSpan(StyleSpan(BOLD), 0, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    span.setSpan(RoundedBackgroundSpan(backgroundColor,foregroundColor,BACKGROUND_CORNER_RADIUS,HORIZONTAL_PADDING,
        VERTICAL_PADDING), 0, span.length-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return span
}
