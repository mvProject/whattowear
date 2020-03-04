package com.kinectpro.whattowear.data

import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.model.trip.TripModel

interface IWeatherRangeSummary {
    fun getTripWeatherCondition(weatherForecast: List<WeatherData>): TripModel?
}
