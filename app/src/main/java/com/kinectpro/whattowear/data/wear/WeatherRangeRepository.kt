package com.kinectpro.whattowear.data.wear


import com.kinectpro.whattowear.data.WeatherData
import com.kinectpro.whattowear.data.wear.model.TempSummary
import com.kinectpro.whattowear.data.wear.model.WeatherCondition
import com.kinectpro.whattowear.utils.getMaximumTempFromRange
import com.kinectpro.whattowear.utils.getMinimumTempFromRange

class WeatherRangeRepository : IWeatherRangeSummary {
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

    override fun getWeatherConditionRange(weatherForecast: List<WeatherData>): List<WeatherCondition> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}