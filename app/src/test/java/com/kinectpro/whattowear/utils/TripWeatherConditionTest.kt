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

    private fun getTestWeatherListWithNulls(): List<WeatherData> {
        return listOf(
            WeatherData(
                1583704800,
                null,
                3f,
                "rain"
            ),
            WeatherData(
                1583791200,
                10.5f,
                null,
                "wind"
            ),
            WeatherData(
                1583877600,
                10f,
                2f,
                "clear-day"
            )
        )
    }

    private fun getTestWeatherListWithZero(): List<WeatherData> {
        return listOf(
            WeatherData(
                1583704800,
                0f,
                3f,
                "rain"
            ),
            WeatherData(
                0,
                10.5f,
                3f,
                "wind"
            ),
            WeatherData(
                1583791200,
                10.5f,
                0f,
                "wind"
            ),
            WeatherData(
                1583877600,
                10f,
                2f,
                "0"
            )
        )
    }

    private val conditionTest = listOf(
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
    private val conditionTestZero = listOf(
        WeatherCondition(
            "rain",
            listOf(1583704800)
        ),
        WeatherCondition(
            "wind",
            listOf(1583791200)
        )
    )

    private val expectedResultZero =
        TripModel(
            TempSummary(
                0f,
                10.5f
            ),
            TempSummary(0f, 3f), conditionTestZero
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

    @Test
    fun getTripWeatherCondition_Null_Values() {
        assertEquals(
            expectedResult,
            TripWeatherCondition().getTripWeatherCondition(getTestWeatherListWithNulls())
        )
    }

    @Test
    fun getTripWeatherCondition_Zero_Values() {
        assertEquals(
            expectedResultZero,
            TripWeatherCondition().getTripWeatherCondition(getTestWeatherListWithZero())
        )
    }
}