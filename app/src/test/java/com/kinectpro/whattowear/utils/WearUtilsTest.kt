package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.R
import org.junit.Test

import org.junit.Assert.*

class WearUtilsTest {

    @Test
    fun convertIconToEnumValue_PROPER_VALUE() {
        val state = "rain"
        assertEquals(2131165335, state.convertIconToDrawable())
    }

    @Test
    fun convertIconToEnumValue_RANDOM_VALUE() {
        val state = "random"
        assertEquals(2131165320, state.convertIconToDrawable())
    }

    @Test
    fun convertIconToEnumValue_NULL_VALUE() {
        val state = null
        assertEquals(2131165320, state.convertIconToDrawable())
    }


    @Test
    fun convertIconToProperConditionName_PROPER_VALUE() {
        val state = "rain"
        assertEquals(R.string.weather_icon_type_rain, state.convertIconToProperConditionName())
    }

    @Test
    fun convertIconToProperConditionName_RANDOM_VALUE() {
        val state = "random"
        assertEquals(R.string.default_weather_description, state.convertIconToProperConditionName())
    }

    @Test
    fun convertIconToProperConditionName_NULL_VALUE() {
        val state = null
        assertEquals(null, state.convertIconToProperConditionName())
    }


    @Test
    fun checkProperConditionState_PROPER_VALUE() {
        val state = "rain"
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