package com.kinectpro.whattowear.utils


import com.kinectpro.whattowear.data.model.wear.WeatherConditionState

fun String?.convertIconToEnumValue(): WeatherConditionState? {
    return when (this) {
        "clear-day" -> WeatherConditionState.CLEARDAY
        "clear-night" -> WeatherConditionState.CLEARNIGHT
        "rain" -> WeatherConditionState.RAIN
        "snow" -> WeatherConditionState.SNOW
        "sleet" -> WeatherConditionState.SLEET
        "wind" -> WeatherConditionState.WIND
        "fog" -> WeatherConditionState.FOG
        "cloudy" -> WeatherConditionState.CLOUDY
        "partly-cloudy-day" -> WeatherConditionState.PARTYCLOUDYDAY
        "partly-cloudy-night" -> WeatherConditionState.PARTYCLOUDYNIGHT
        else -> return null
    }
}

fun String?.checkProperConditionState(): Boolean {
    val condition = listOf(
        "clear-day",
        "clear-night",
        "rain",
        "snow",
        "sleet",
        "wind",
        "fog",
        "cloudy",
        "partly-cloudy-day",
        "partly-cloudy-night"
    )
    return when (this) {
        in condition -> true
        else -> false
    }
}



