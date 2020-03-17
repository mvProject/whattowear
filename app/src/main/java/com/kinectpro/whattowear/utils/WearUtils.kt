package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.R

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

fun String?.convertIconToWeatherRecommendation(): Int? {
    return when (this) {
        "rain" -> R.string.weather_rain_recommendation
        "snow" -> R.string.weather_snow_recommendation
        "sleet" -> R.string.weather_sleet_recommendation
        "fog" -> R.string.weather_fog_recommendation
        else -> return null
    }
}

fun String?.checkIconIsWeatherCondition(): Boolean {
    val default =
        listOf("clear-day", "clear-night", "cloudy", "partly-cloudy-day", "partly-cloudy-night")
    return when (this) {
        in default -> false
        else -> true
    }
}

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



