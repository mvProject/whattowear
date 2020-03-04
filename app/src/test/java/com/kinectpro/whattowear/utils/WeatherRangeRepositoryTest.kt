package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.wear.WeatherRangeRepository
import com.kinectpro.whattowear.data.wear.model.TempSummary
import org.junit.Test

import org.junit.Assert.*

class WeatherRangeRepositoryTest {

    private val tempList = listOf(-10.5f, -5.5f, 0.5f, 5.5f, 10.5f)
    private val emptyList = listOf<Float>()

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
}