package com.kinectpro.whattowear.data

import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.model.trip.TempSummary
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.data.model.trip.WeatherCondition
import com.kinectpro.whattowear.utils.*

class TripWeatherCondition :
    IWeatherRangeSummary {
    /**
     * @param weatherForecast list of weather data items`
     * @return list of states with dates of appearing
     */
    override fun getTripWeatherCondition(weatherForecast: List<WeatherData>): TripModel? {
        val dayMinMax = getTempMinMaxValue(weatherForecast.getDayTemperatureAsList())
        val nightMinMax = getTempMinMaxValue(weatherForecast.getNightTemperatureAsList())
        val conditions = getWeatherStateConditionAppearances(weatherForecast)
        if ((dayMinMax != null) and (nightMinMax != null)) {
            return TripModel(
                dayMinMax!!,
                nightMinMax!!,
                conditions
            )
        }
        return null
    }

    private fun getTempMinMaxValue(tempForecast: List<Float>?): TempSummary? {
        tempForecast?.let {
            if ((it.min() != null) and (it.max() != null))
                return TempSummary(
                    it.min()!!,
                    it.max()!!
                )
            return null
        }
        return null
    }

    private fun getWeatherStateConditionAppearances(weatherForecast: List<WeatherData>): List<WeatherCondition> {
        val conditions = mutableListOf<WeatherCondition>()

        for (state in weatherForecast.getWeatherStatesUniqueAsList()) {
            val stateDates = weatherForecast.filter { it.weatherState == state }
            if (state.checkProperConditionState()) {
                conditions.add(
                    WeatherCondition(
                        state,
                        stateDates.filter { it.time > 0 }.map { it.time })
                )
            }
        }
        return conditions
    }
}