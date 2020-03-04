package com.kinectpro.whattowear.utils


import com.kinectpro.whattowear.data.wear.model.WeatherConditionState

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




