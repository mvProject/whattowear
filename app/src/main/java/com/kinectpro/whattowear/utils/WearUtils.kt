package com.kinectpro.whattowear.utils


import com.kinectpro.whattowear.data.wear.model.WeatherConditionState

/**
 * Returns minimum value from float range or null if not exists
 * @param tempRange list of temperature from forecast
 */
fun getMinimumTempFromRange(tempRange: List<Float>?): Float? {
    return tempRange?.min()
}

/**
 * Returns maximum value from float range or null if not exists
 * @param tempRange list of temperature from forecast
 */
fun getMaximumTempFromRange(tempRange: List<Float>?): Float? {
    return tempRange?.max()
}

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




