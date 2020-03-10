package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.wear.WeatherConditionState
import org.junit.Test

import org.junit.Assert.*

class WearUtilsTest {

    @Test
    fun convertIconToEnumValue_PROPER_VALUE() {
        val state = "clear-day"
        assertEquals(WeatherConditionState.CLEARDAY, state.convertIconToDrawable())
    }

    @Test
    fun convertIconToEnumValue_RANDOM_VALUE() {
        val state = "random"
        assertEquals(null, state.convertIconToDrawable())
    }

    @Test
    fun convertIconToEnumValue_NULL_VALUE() {
        val state = null
        assertEquals(null, state.convertIconToDrawable())
    }

    @Test
    fun checkProperConditionState_PROPER_VALUE() {
        val state = "clear-day"
        assertEquals(true, state.checkProperConditionState())
    }

    @Test
    fun checkProperConditionState_RANDOM_VALUE() {
        val state = "random"
        assertEquals(false, state.checkProperConditionState())
    }

    @Test
    fun checkProperConditionState_NULL_VALUE() {
        val state = null
        assertEquals(false, state.checkProperConditionState())
    }
}