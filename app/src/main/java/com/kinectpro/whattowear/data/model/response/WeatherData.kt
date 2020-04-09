package com.kinectpro.whattowear.data.model.response

data class WeatherData(
    val time: Long,
    val temperatureDay: Float?,
    val temperatureNight: Float?,
    val weatherState: String?
)

data class DarkSkyWeather(
    val latitude: Double,
    val longitude: Double,
    val daily: Daily,
    val offset: Float
)

data class Daily(
    val icon: String?,
    val data: MutableList<Data>
)

data class Data(
    val icon: String? = "",
    val sunsetTime: Long?,
    val apparentTemperatureHigh: Float?,
    val apparentTemperatureLow: Float?
)