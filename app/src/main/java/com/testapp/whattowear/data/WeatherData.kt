package com.testapp.whattowear.data

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("time")
    val time : String,
    @SerializedName("apparentTemperatureHigh")
    val temperature : String,
    @SerializedName("apparentTemperatureHighTime")
    val temperatureTime : String
)