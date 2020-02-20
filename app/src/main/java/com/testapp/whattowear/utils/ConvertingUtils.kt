package com.testapp.whattowear.utils

import com.testapp.whattowear.data.DarkSkyWeather
import com.testapp.whattowear.data.WeatherData

fun DarkSkyWeather.convertToWeatherDataModel() : WeatherData{
    return WeatherData(
        this.daily.data[0].time.toString(),
        this.daily.data[0].apparentTemperatureHigh.toString(),
        this.daily.data[0].apparentTemperatureHighTime.toString()
    )
}