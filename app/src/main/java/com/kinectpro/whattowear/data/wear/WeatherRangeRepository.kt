package com.kinectpro.whattowear.data.wear


import com.kinectpro.whattowear.data.WeatherData
import com.kinectpro.whattowear.data.wear.model.TempSummary
import com.kinectpro.whattowear.data.wear.model.WeatherCondition
import com.kinectpro.whattowear.utils.*

class WeatherRangeRepository : IWeatherRangeSummary {
    /**
     * @param tempForecast list of temp values from weather forecast
     * @return incstance of TempSummary with min and max value
     */
    override fun getTempMinMaxValue(tempForecast: List<Float>?): TempSummary? {
        tempForecast?.let {
            if ((getMinimumTempFromRange(it) != null) and (getMaximumTempFromRange(it) != null))
                return TempSummary(
                    getMinimumTempFromRange(it)!!,
                    getMaximumTempFromRange(it)!!
                )
            return null
        }
        return null
    }

    /**
     * @param weatherForecast list of weather data items
     * @return list of states with dates of appearing
     */
    override fun getWeatherConditionRange(weatherForecast: List<WeatherData>): List<WeatherCondition> {
        val conditions = mutableListOf<WeatherCondition>()
        for (state in weatherForecast.getWeatherStatesUniqueAsList()) {
            val stateDates = weatherForecast.filter { it.weatherState == state }
            conditions.add(WeatherCondition(state, stateDates.map { it.time }))
        }
        return conditions
    }

}