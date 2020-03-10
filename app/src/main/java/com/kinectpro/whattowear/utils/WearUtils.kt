package com.kinectpro.whattowear.utils


import com.kinectpro.whattowear.R
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

fun String?.convertIconToProperConditionName(): Int? {
    return when (this) {
        "clear-day" -> R.string.weather_icon_type_clear_day
        "clear-night" -> R.string.weather_icon_type_clear_night
        "rain" -> R.string.weather_icon_type_rain
        "snow" -> R.string.weather_icon_type_snow
        "sleet" -> R.string.weather_icon_type_sleet
        "wind" -> R.string.weather_icon_type_wind
        "fog" -> R.string.weather_icon_type_fog
        "cloudy" -> R.string.weather_icon_type_cloudy
        "partly-cloudy-day" -> R.string.weather_icon_type_partly_cloudy_day
        "partly-cloudy-night" -> R.string.weather_icon_type_partly_cloudy_night
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



