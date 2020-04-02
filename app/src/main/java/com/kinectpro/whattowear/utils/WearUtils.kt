package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.R

/**
 * Extension for String variable to convert
 * adjusted weather condition from string icon to appropriate drawable icon
 * @return id of proper drawable resource
 */
fun String?.convertIconToDrawable(): Int {
    return when (this) {
        "rain" -> R.drawable.ic_rain
        "snow" -> R.drawable.ic_snow
        "sleet" -> R.drawable.ic_sleet
        "wind" -> R.drawable.ic_wind
        "fog" -> R.drawable.ic_weather_fog
        else -> R.drawable.ic_clear_day
    }
}

/**
 * Extension for String variable to convert
 * adjusted weather condition from string icon to appropriate background
 * @return id of proper drawable resource
 */
fun String?.convertIconToDrawableBackground(): Int {
    return when (this) {
        "rain" -> R.drawable.bg_rainy
        "wind" -> R.drawable.bg_windy
        else -> R.drawable.bg_sunny
    }
}

/**
 * Extension for String variable to convert
 * adjusted weather condition from string icon to appropriate condition expectation
 * @return id of proper string resource
 */
fun String?.convertIconToProperConditionName(): Int? {
    return when (this) {
        "rain" -> R.string.weather_icon_type_rain
        "snow" -> R.string.weather_icon_type_snow
        "sleet" -> R.string.weather_icon_type_sleet
        "wind" -> R.string.weather_icon_type_wind
        "fog" -> R.string.weather_icon_type_fog
        null -> return null
        else -> R.string.default_weather_description
    }
}

/**
 * Extension for String variable to convert
 * adjusted weather condition from string icon to appropriate wear recommendation
 * @return id of proper string resource
 */
fun String?.convertIconToWeatherRecommendation(): Int? {
    return when (this) {
        "rain" -> R.string.weather_rain_recommendation
        "snow" -> R.string.weather_snow_recommendation
        "sleet" -> R.string.weather_sleet_recommendation
        "fog" -> R.string.weather_fog_recommendation
        else -> return null
    }
}

/**
 * Extension for String variable to check
 * is api given weather condition is different from predefined default weather conditions
 * @return true when different, false when matched
 */
fun String?.checkIconIsWeatherCondition(): Boolean {
    val default =
        listOf("clear-day", "clear-night", "cloudy", "partly-cloudy-day", "partly-cloudy-night")
    return when (this) {
        in default -> false
        else -> true
    }
}

/**
 * Extension for String variable to check
 * is given weather condition is match predefined weather conditions
 * @return true when match, otherwise false
 */
fun String?.checkProperConditionState(): Boolean {
    val condition = listOf(
        DEFAULT_WEATHER_STATE,
        "rain",
        "snow",
        "sleet",
        "wind",
        "fog"
    )
    return when (this) {
        in condition -> true
        else -> false
    }
}



