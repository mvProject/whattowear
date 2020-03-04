package com.kinectpro.whattowear.data.wear.model.trip

data class TripModel(
    val dayTemp: TempSummary,
    val nightTemp: TempSummary,
    val conditionDates: List<WeatherCondition>
)