package com.testapp.whattowear.utils

import com.testapp.whattowear.data.DarkSkyWeather
import com.testapp.whattowear.data.WeatherData

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