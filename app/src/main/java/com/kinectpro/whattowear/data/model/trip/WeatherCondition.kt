package com.kinectpro.whattowear.data.model.trip

/**
 * Class for holding specified weather condition dates
 * @param condition weather which are present in forecast (like rain)
 * @param dates list of dates in string format (dd/MM) when specified condition expected
 */
data class WeatherCondition(val condition: String, val dates: List<Long>)