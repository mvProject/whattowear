package com.kinectpro.whattowear.data.model.trip

data class TripModel(
    val dayTemp: TempSummary,
    val nightTemp: TempSummary,
    val conditionDates: List<WeatherCondition>
)