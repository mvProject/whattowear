package com.testapp.whattowear.utils

import com.testapp.whattowear.data.DarkSkyWeather
import com.testapp.whattowear.data.WeatherData
import java.text.SimpleDateFormat
import java.util.*

const val DATE_READABLE_PATTERN = "dd/MM/yy"

fun DarkSkyWeather.convertToWeatherDataModel(): WeatherData? {
    if ((this.daily.data.first().time != null) and (this.daily.data.first().apparentTemperatureHigh != null) and (this.daily.data.first().apparentTemperatureHighTime != null)) {
        return WeatherData(
            this.daily.data.first().time.toString(),
            this.daily.data.first().apparentTemperatureHigh.toString(),
            this.daily.data.first().apparentTemperatureHighTime?.toString() ?: "null"
        )
    }
    return null
}

fun Long.getDateToReadableFormat(): String? {
    if (this > 0) return SimpleDateFormat(
        DATE_READABLE_PATTERN,
        Locale.getDefault()
    ).format(this)
    return null
}




