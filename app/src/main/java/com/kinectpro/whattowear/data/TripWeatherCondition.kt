package com.kinectpro.whattowear.data

import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.model.trip.TempSummary
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.data.model.trip.WeatherCondition
import com.kinectpro.whattowear.utils.checkProperConditionState
import com.kinectpro.whattowear.utils.getDayTemperatureAsList
import com.kinectpro.whattowear.utils.getNightTemperatureAsList
import com.kinectpro.whattowear.utils.getWeatherStatesUniqueAsList

class TripWeatherCondition :
    IWeatherRangeSummary {

    /**
     * Returns TripModel object for given forecast
     * @param weatherForecast list of weather data items`
     * return null if day or night temp summary is null
     */
    override fun getTripWeatherCondition(weatherForecast: List<WeatherData>): TripModel? {
        val dayMinMax = getTempMinMaxValue(weatherForecast.getDayTemperatureAsList())
        val nightMinMax = getTempMinMaxValue(weatherForecast.getNightTemperatureAsList())
        val conditions = getWeatherStateConditionAppearances(weatherForecast) as MutableList
        if ((dayMinMax != null) && (nightMinMax != null)) {
            return TripModel(
                dayMinMax,
                nightMinMax,
                conditions
            )
        }
        return null
    }

    /**
     *  Returning TempSummary object for given temp list
     *  @param tempForecast list of tempetature values from selected date range
     *  @return null if tempForecast is null or of min or max elements are null
     */
    private fun getTempMinMaxValue(tempForecast: List<Float>?): TempSummary? {
        tempForecast?.let {
            if ((it.min() != null) && (it.max() != null))
                return TempSummary(
                    it.min()!!,
                    it.max()!!
                )
            return null
        }
        return null
    }

    /**
     * Returns list of specified weather conditions with dates of their appearances
     * @param weatherForecast list of weather data items
     */
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
        if (conditions.isEmpty()) {
            conditions.add(WeatherCondition(DEFAULT_WEATHER_STATE, listOf()))
        }
        return conditions
    }
}