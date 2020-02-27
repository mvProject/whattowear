package com.testapp.whattowear.utils

import android.content.Context
import com.testapp.whattowear.R
import com.testapp.whattowear.data.DarkSkyWeather
import com.testapp.whattowear.data.WeatherData
import java.text.SimpleDateFormat
import java.util.*

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

fun Long.getDateToReadableFormat(context: Context): String? {
    if (this > 0) return SimpleDateFormat(
        context.getString(R.string.date_readable_pattern),
        Locale.getDefault()
    ).format(this)
    return null
}