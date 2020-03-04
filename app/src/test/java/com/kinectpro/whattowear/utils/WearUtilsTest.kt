package com.kinectpro.whattowear.utils
import com.kinectpro.whattowear.data.wear.model.WeatherConditionState
import org.junit.Test

import org.junit.Assert.*

class WeatherUtilsTest {

    private val tempRange = listOf(-10.5f, 0.5f, 10.5f)
    private val zeroRange = listOf<Float>()

    @Test
    fun getMinimumTempFromRange_TEMP_RANGE() {
        assertEquals(-10.5f, getMinimumTempFromRange(tempRange))
    }

    @Test
    fun getMaximumTempFromRange_TEMP_RANGE() {
        assertEquals(10.5f, getMaximumTempFromRange(tempRange))
    }

    @Test
    fun getMinimumTempFromRange_ZERO_TEMP_RANGE() {
        assertEquals(null, getMinimumTempFromRange(zeroRange))
    }

    @Test
    fun getMaximumTempFromRange_ZERO_TEMP_RANGE() {
        assertEquals(null, getMaximumTempFromRange(zeroRange))
    }

    @Test
    fun getMinimumTempFromRange_NULL() {
        assertEquals(null, getMinimumTempFromRange(null))
    }

    @Test
    fun getMaximumTempFromRange_NULL() {
        assertEquals(null, getMaximumTempFromRange(null))
    }

    @Test
    fun convertIconToEnumValue_PROPER_VALUE() {
        val state = "clear-day"
        assertEquals(WeatherConditionState.CLEARDAY, state.convertIconToEnumValue())
    }

    @Test
    fun convertIconToEnumValue_RANDOM_VALUE() {
        val state = "random"
        assertEquals(null, state.convertIconToEnumValue())
    }

    @Test
    fun convertIconToEnumValue_NULL_VALUE() {
        val state = null
        assertEquals(null, state.convertIconToEnumValue())
    }
}