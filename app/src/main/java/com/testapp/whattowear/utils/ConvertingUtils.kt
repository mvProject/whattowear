package com.testapp.whattowear.utils

import com.testapp.whattowear.data.DarkSkyWeather
import com.testapp.whattowear.data.WeatherData

fun DarkSkyWeather.convertToWeatherDataModel() : WeatherData{
    return WeatherData(
        this.daily.data.first().time.toString(),
        this.daily.data.first().apparentTemperatureHigh.toString(),
        this.daily.data.first().apparentTemperatureHighTime.toString()
    )
}