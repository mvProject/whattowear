package com.kinectpro.whattowear.data.wear

import com.kinectpro.whattowear.data.wear.model.TempSummary
import com.kinectpro.whattowear.data.WeatherData
import com.kinectpro.whattowear.data.wear.model.WeatherCondition

interface IWeatherRangeSummary {
    fun getTempMinMaxValue(tempForecast: List<Float>?): TempSummary?
    fun getWeatherConditionRange(weatherForecast: List<WeatherData>): List<WeatherCondition>
}
