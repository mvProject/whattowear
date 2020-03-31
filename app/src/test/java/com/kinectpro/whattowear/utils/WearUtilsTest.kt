package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.R
import org.junit.Test

import org.junit.Assert.*

class WearUtilsTest {

    @Test
    fun convertIconToEnumValue_PROPER_VALUE() {
        val stateRain = "rain"
        val stateSnow = "snow"
        val stateSleet = "sleet"
        val stateWind = "wind"
        val stateFog = "fog"
        val state = "else"
        assertEquals(2131165335, stateRain.convertIconToDrawable())
        assertEquals(2131165337, stateSnow.convertIconToDrawable())
        assertEquals(2131165336, stateSleet.convertIconToDrawable())
        assertEquals(2131165340, stateWind.convertIconToDrawable())
        assertEquals(2131165338, stateFog.convertIconToDrawable())
        assertEquals(2131165320, state.convertIconToDrawable())
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
        val stateRain = "rain"
        val stateSnow = "snow"
        val stateSleet = "sleet"
        val stateFog = "fog"
        assertEquals(R.string.weather_icon_type_rain, stateRain.convertIconToProperConditionName())
        assertEquals(R.string.weather_icon_type_snow, stateSnow.convertIconToProperConditionName())
        assertEquals(
            R.string.weather_icon_type_sleet,
            stateSleet.convertIconToProperConditionName()
        )
        assertEquals(R.string.weather_icon_type_fog, stateFog.convertIconToProperConditionName())
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