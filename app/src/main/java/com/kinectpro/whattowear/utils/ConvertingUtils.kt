package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.DarkSkyWeather
import com.kinectpro.whattowear.data.WeatherData
import java.text.SimpleDateFormat
import java.util.*

/**
 * Specified pattern to convert long variable timestamp
 */
const val STATE_DATE_READABLE_PATTERN = "dd/MM"

/**
 * Extension Method to response data class which
 * try to extract only specified non-null fields
 * @return instance of WeatherData data class or null
 */
fun DarkSkyWeather.convertToWeatherDataModel(): WeatherData? {
    if ((this.daily.data.first().time != null) and (this.daily.data.first().apparentTemperatureHigh != null) and (this.daily.data.first().apparentTemperatureLow != null)) {
        return WeatherData(
            this.daily.data.first().time.toString(),
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
 * Returns list of day temp from weather forecast list
 */
fun List<WeatherData>.getDayTemperatureAsList(): List<Float> {
    val tempResultList = mutableListOf<Float>()
    for (temp in this) {
        tempResultList.add(temp.temperatureDay)
    }
    return tempResultList
}

/**
 * Returns list of day temp from weather forecast list
 */
fun List<WeatherData>.getNightTemperatureAsList(): List<Float> {
    val tempResultList = mutableListOf<Float>()
    for (temp in this) {
        tempResultList.add(temp.temperatureNight)
    }
    return tempResultList
}




