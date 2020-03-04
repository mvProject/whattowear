package com.kinectpro.whattowear.data.wear

import com.kinectpro.whattowear.data.wear.model.TripModel

interface IWeatherRangeSummary {
    fun getTripWeatherCondition(weatherForecast: List<WeatherData>): TripModel?
}
