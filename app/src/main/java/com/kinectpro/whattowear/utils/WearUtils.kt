package com.kinectpro.whattowear.utils


import com.kinectpro.whattowear.data.model.wear.WeatherConditionState
import com.kinectpro.whattowear.R

fun String?.convertIconToDrawable(): Int {
    return when (this) {
        "clear-day" -> R.drawable.ic_weather_clear_day
        "clear-night" -> R.drawable.ic_weather_clear_night
        "rain" -> R.drawable.ic_weather_rain
        "snow" -> R.drawable.ic_weather_snow
        "sleet" -> R.drawable.ic_weather_sleet
        "wind" -> R.drawable.ic_weather_wind
        "fog" -> R.drawable.ic_weather_fog
        "cloudy" -> R.drawable.ic_weather_cloudy
        "partly-cloudy-day" -> R.drawable.ic_weather_partly_cloudy_day
        "partly-cloudy-night" -> R.drawable.ic_weather_party_cloudy_night
        else -> R.drawable.ic_weather_placeholder
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



