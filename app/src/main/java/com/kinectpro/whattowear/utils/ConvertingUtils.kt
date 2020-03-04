package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.response.DarkSkyWeather
import com.kinectpro.whattowear.data.response.WeatherData
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Specified pattern to convert long variable timestamp
 */
const val STATE_DATE_READABLE_PATTERN = "dd.MM"
const val DATE_READABLE_PATTERN = "dd/MM/yy"

/**
 * Extension Method to response data class which
 * try to extract only specified non-null fields
 * @return instance of WeatherData data class or null
 */
fun DarkSkyWeather.convertToWeatherDataModel(): WeatherData? {
    if ((this.daily.data.first().time != null) and (this.daily.data.first().apparentTemperatureHigh != null) and (this.daily.data.first().apparentTemperatureLow != null)) {
        return WeatherData(
            this.daily.data.first().time!!,
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
        tempResultList.add(temp.temperatureDay)
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
        tempResultList.add(temp.temperatureNight)
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
            tempResultList.add(it)
        }
    }
    return tempResultList.distinct()
}

fun List<Long>.convertToShortDateFormatString(): String {
    val result = StringBuilder()
    for (date in this) {
            result.append(TimeUnit.SECONDS.toMillis(date).convertDateToReadableFormat(STATE_DATE_READABLE_PATTERN) + ",")
        }
    return result.substring(0,result.length-1).toString()
}






