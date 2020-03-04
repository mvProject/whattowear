package com.kinectpro.whattowear.data.wear

import com.kinectpro.whattowear.data.response.WeatherData
import com.kinectpro.whattowear.data.wear.model.trip.TripModel

interface IWeatherRangeSummary {
    fun getTripWeatherCondition(weatherForecast: List<WeatherData>): TripModel?
}
