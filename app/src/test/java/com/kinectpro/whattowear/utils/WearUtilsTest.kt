package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.wear.WeatherConditionState
import org.junit.Test

import org.junit.Assert.*

class WearUtilsTest {

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