package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.WeatherData
import com.kinectpro.whattowear.data.wear.WeatherRangeRepository
import com.kinectpro.whattowear.data.wear.model.TempSummary
import com.kinectpro.whattowear.data.wear.model.WeatherCondition
import org.junit.Test

import org.junit.Assert.*

class WeatherRangeRepositoryTest {

    private val tempList = listOf(-10.5f, -5.5f, 0.5f, 5.5f, 10.5f)
    private val emptyList = listOf<Float>()

    private fun getStatesAppearanceTempList(): List<WeatherCondition> {
        return listOf(
            WeatherCondition("rain", listOf(1583704800)),
            WeatherCondition("wind", listOf(1583791200, 1584050400)),
            WeatherCondition("clear-day", listOf(1583877600))
        )
    }

    private fun getExpectedWeatherList(): List<WeatherData> {
        return listOf(
            WeatherData(1583704800, 11f, 3f, "rain"),
            WeatherData(1583791200, 10.5f, 2.5f, "wind"),
            WeatherData(1583877600, 10f, 2f, "clear-day"),
            WeatherData(1583964000, 9f, 1f, null),
            WeatherData(1584050400, 8f, 3f, "wind")
        )
    }

    @Test
    fun getTempMinMaxValue_LIST() {
        assertEquals(
            TempSummary(-10.5f, 10.5f),
            WeatherRangeRepository().getTempMinMaxValue(tempList)
        )
    }

    @Test
    fun getTempMinMaxValue_EMPTY_LIST() {
        assertEquals(null, WeatherRangeRepository().getTempMinMaxValue(emptyList))
    }

    @Test
    fun getTempMinMaxValue_NULL() {
        assertEquals(null, WeatherRangeRepository().getTempMinMaxValue(null))
    }

    @Test
    fun getWeatherStateAppearanceInDateRange() {
        assertEquals(
            getStatesAppearanceTempList(),
            WeatherRangeRepository().getWeatherConditionRange(getExpectedWeatherList())
        )
    }
}