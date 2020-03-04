package com.kinectpro.whattowear.data

data class WeatherData(
    val time: String,
    val temperatureDay: Float,
    val temperatureNight: Float,
    val weatherState: String?
)

data class DarkSkyWeather(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val currently: Data,
    val daily: Daily
)

data class Daily(
    val summary: String?,
    val icon: String?,
    val data: MutableList<Data>
)

data class Data(
    val time: Long?,
    val summary: String? = "",
    val icon: String? = "",
    val nearestStormDistance: Int?,
    val nearestStormBearing: Int?,
    val precipIntensity: Double?,
    val precipProbability: Double?,
    val temperature: Double?,
    val apparentTemperature: Double?,
    val apparentTemperatureHigh: Float?,
    val apparentTemperatureLow: Float?,
    val apparentTemperatureHighTime: Long?,
    val apparentTemperatureLowTime: Long?,
    val dewPoint: Double?,
    val humidity: Double?,
    val pressure: Double?,
    val windSpeed: Double?,
    val windGust: Double?,
    val windGustTime: Long?,
    val windBearing: Int?,
    val cloudCover: Double?,
    val uvIndex: Int?,
    val uvIndexTime: Long?,
    val visibility: Double?,
    val ozone: Double?
)