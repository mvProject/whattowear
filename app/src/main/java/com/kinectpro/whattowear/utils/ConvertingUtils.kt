package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.response.DarkSkyWeather
import com.kinectpro.whattowear.data.model.response.WeatherData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val STATE_DATE_READABLE_PATTERN = "dd.MM"
const val DATE_READABLE_PATTERN = "dd/MM/yy"
const val DEFAULT_WEATHER_STATE = "defaultWeatherState"
const val METRIC_TYPE_CELSIUS = "°C"
const val METRIC_TYPE_FAHRENHEIT = "°F"
const val LANGUAGE_RU = "ru"
const val LANGUAGE_UA = "uk"
const val LANGUAGE_EN = "en"
const val LOCALE_COUNTRY_US = "US"
const val LOCALE_LANGUAGE_RU = "ru_RU"
const val LOCALE_LANGUAGE_UA = "uk_UA"

/**
 *
 */
const val BACKGROUND_CORNER_RADIUS = 20
const val HORIZONTAL_PADDING = 15
const val VERTICAL_PADDING = 1
/**
 * Extension Method to response data class which
 * try to extract only specified non-null fields
 * Return instance of WeatherData data class
 * Return null if sunsetTime or apparentTemperatureHigh or apparentTemperatureLow is null
 */
fun DarkSkyWeather.convertToWeatherDataModel(): WeatherData? {
    if ((this.daily.data.first().sunsetTime != null) && (this.daily.data.first().apparentTemperatureHigh != null) && (this.daily.data.first().apparentTemperatureLow != null)) {
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
 * Obtain response language according locale
 * @return language for response
 */
fun convertCurrentLocaleLanguageToApiLanguageFormat(): String {
    return when (Locale.getDefault().toString()) {
        LOCALE_LANGUAGE_RU -> LANGUAGE_RU
        LOCALE_LANGUAGE_UA -> LANGUAGE_UA
        else -> LANGUAGE_EN
    }
}

/**
 * Extension obtaining temp according locale metric system
 * @return temperature value
 */
fun Float?.getProperMetricTempValue(): Int? {
    return this?.let {
        when (Locale.getDefault().country) {
            LOCALE_COUNTRY_US -> this.convertCelsiusToFahrenheit()!!.roundToInt()
            else -> this.roundToInt()
        }
    }
}

/**
 * Convert temperature value from Celsius to Fahrenheit metric
 */
fun Float?.convertCelsiusToFahrenheit(): Float? {
    return this?.let {
        (1.8f * this) + 32
    }
}