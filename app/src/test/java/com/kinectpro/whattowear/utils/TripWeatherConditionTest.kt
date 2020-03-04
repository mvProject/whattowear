package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.TripWeatherCondition
import com.kinectpro.whattowear.data.model.trip.TempSummary
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.data.model.trip.WeatherCondition
import org.junit.Test

import org.junit.Assert.*

class TripWeatherConditionTest {

    private fun getTestWeatherList(): List<WeatherData> {
        return listOf(
            WeatherData(
                1583704800,
                11f,
                3f,
                "rain"
            ),
            WeatherData(
                1583791200,
                10.5f,
                2.5f,
                "wind"
            ),
            WeatherData(
                1583877600,
                10f,
                2f,
                "clear-day"
            ),
            WeatherData(
                1583964000,
                9f,
                1f,
                null
            ),
            WeatherData(
                1584050400,
                8f,
                3f,
                "wind"
            )
        )
    }

    private val conditionTest = listOf<WeatherCondition>(
        WeatherCondition(
            "rain",
            listOf(1583704800)
        ),
        WeatherCondition(
            "wind",
            listOf(1583791200, 1584050400)
        ),
        WeatherCondition(
            "clear-day",
            listOf(1583877600)
        )
    )

    private val expectedResult =
        TripModel(
            TempSummary(
                8f,
                11f
            ),
            TempSummary(1f, 3f), conditionTest
        )

    @Test
    fun getTripWeatherCondition_Proper() {
        assertEquals(
            expectedResult,
            TripWeatherCondition().getTripWeatherCondition(getTestWeatherList())
        )
    }
}