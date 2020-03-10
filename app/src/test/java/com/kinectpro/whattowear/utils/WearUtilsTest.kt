package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.R
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

    @Test
    fun convertIconToProperConditionName_PROPER_VALUE() {
        val state = "clear-day"
        assertEquals(R.string.weather_icon_type_clear_day, state.convertIconToProperConditionName())
    }

    @Test
    fun convertIconToProperConditionName_RANDOM_VALUE() {
        val state = "random"
        assertEquals(null, state.convertIconToProperConditionName())
    }

    @Test
    fun convertIconToProperConditionName_NULL_VALUE() {
        val state = null
        assertEquals(null, state.convertIconToProperConditionName())
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