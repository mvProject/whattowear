package com.kinectpro.whattowear.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
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
const val HORIZONTAL_PADDING = 10
const val VERTICAL_PADDING = 1

const val DATE_PADDING = 5
const val DATE_PADDING_SEPARATOR = 5

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
 * Extension to convert list of dates to specified short date united string
 * @return single string value
 */
fun List<Long>.convertToShortDateFormatString(): String {
    val result = StringBuilder()
    for (date in this) {
        result.append(
            TimeUnit.SECONDS.toMillis(date).convertDateToReadableFormat(
                STATE_DATE_READABLE_PATTERN
            ) + " "
        )
    }
    return result.toString()
}

/**
 * Extension convert temp summary to specified string
 * @return single string value
 */
fun TempSummary.convertToReadableRange(): StringBuilder {
    return StringBuilder().apply {
        append("Max:  ${maxValue.roundToInt()} ")
        append("°C")
        append("\n")
        append("Min:  ${minValue.roundToInt()} ")
        append("°C")
    }
}

fun getDatesListAsRoundedBackgroundSpannable(
    datesStringResourceToSpan: String?,
    backgroundColor: Int,
    textColor: Int
): SpannableString? {
    if (datesStringResourceToSpan != null) {
        val span = SpannableString(datesStringResourceToSpan)
        for (i in span.indices step 6) {
            span.setSpan(StyleSpan(Typeface.BOLD), i, i + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            span.setSpan(
                RoundedBackgroundSpan(
                    backgroundColor,
                    textColor,
                    BACKGROUND_CORNER_RADIUS,
                    HORIZONTAL_PADDING,
                    VERTICAL_PADDING
                ),
                i,
                i + 5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return span
    } else {
        return null
    }
}





