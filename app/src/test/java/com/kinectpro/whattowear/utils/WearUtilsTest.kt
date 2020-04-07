package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.R
import org.junit.Assert.assertEquals
import org.junit.Test

class WearUtilsTest {

    @Test
    fun convertIconToEnumValue_PROPER_VALUE() {
        val stateRain = "rain"
        val stateSnow = "snow"
        val stateSleet = "sleet"
        val stateWind = "wind"
        val stateFog = "fog"
        val state = "else"
        assertEquals(R.drawable.ic_rain, stateRain.convertIconToDrawable())
        assertEquals(R.drawable.ic_snow, stateSnow.convertIconToDrawable())
        assertEquals(R.drawable.ic_sleet, stateSleet.convertIconToDrawable())
        assertEquals(R.drawable.ic_wind, stateWind.convertIconToDrawable())
        assertEquals(R.drawable.ic_weather_fog, stateFog.convertIconToDrawable())
        assertEquals(R.drawable.ic_clear_day, state.convertIconToDrawable())
    }

    @Test
    fun convertIconToEnumValue_RANDOM_VALUE() {
        val state = "random"
        assertEquals(R.drawable.ic_clear_day, state.convertIconToDrawable())
    }

    @Test
    fun convertIconToEnumValue_NULL_VALUE() {
        val state = null
        assertEquals(R.drawable.ic_clear_day, state.convertIconToDrawable())
    }

    @Test
    fun convertIconToDrawableBackground_PROPER_VALUE() {
        val stateRain = "rain"
        val stateWind = "wind"
        val stateSnow = "snow"
        val state = "else"
        assertEquals(R.drawable.bg_rainy, stateRain.convertIconToDrawableBackground())
        assertEquals(R.drawable.bg_windy, stateWind.convertIconToDrawableBackground())
        assertEquals(R.drawable.bg_snowy, stateSnow.convertIconToDrawableBackground())
        assertEquals(R.drawable.bg_sunny, state.convertIconToDrawableBackground())
    }

    @Test
    fun convertIconToDrawableBackground_RANDOM_VALUE() {
        val state = "random"
        assertEquals(R.drawable.bg_sunny, state.convertIconToDrawableBackground())
    }

    @Test
    fun convertIconToDrawableBackground_NULL_VALUE() {
        val state = null
        assertEquals(R.drawable.bg_sunny, state.convertIconToDrawableBackground())
    }

    @Test
    fun convertIconToFontColor_PROPER_VALUE() {
        val stateRain = "rain"
        assertEquals(R.color.colorPrimaryLight, stateRain.convertIconToFontColor())
    }

    @Test
    fun convertIconToFontColor_RANDOM_VALUE() {
        val state = "wind"
        assertEquals(R.color.colorPrimary, state.convertIconToFontColor())
    }

    @Test
    fun convertIconToFontColor_NULL_VALUE() {
        val state = null
        assertEquals(R.color.colorPrimary, state.convertIconToFontColor())
    }

    @Test
    fun convertIconToProperConditionName_PROPER_VALUE() {
        val stateRain = "rain"
        val stateSnow = "snow"
        val stateSleet = "sleet"
        val stateFog = "fog"
        val stateWind = "wind"
        assertEquals(R.string.weather_icon_type_rain, stateRain.convertIconToProperConditionName())
        assertEquals(R.string.weather_icon_type_snow, stateSnow.convertIconToProperConditionName())
        assertEquals(
            R.string.weather_icon_type_sleet,
            stateSleet.convertIconToProperConditionName()
        )
        assertEquals(R.string.weather_icon_type_fog, stateFog.convertIconToProperConditionName())
        assertEquals(R.string.weather_icon_type_wind, stateWind.convertIconToProperConditionName())
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

    @Test
    fun convertIconToWeatherRecommendation_PROPER_VALUE() {
        val stateRain = "rain"
        val stateSnow = "snow"
        val stateSleet = "sleet"
        val stateFog = "fog"
        assertEquals(
            R.string.weather_rain_recommendation,
            stateRain.convertIconToWeatherRecommendation()
        )
        assertEquals(
            R.string.weather_snow_recommendation,
            stateSnow.convertIconToWeatherRecommendation()
        )
        assertEquals(
            R.string.weather_sleet_recommendation,
            stateSleet.convertIconToWeatherRecommendation()
        )
        assertEquals(
            R.string.weather_fog_recommendation,
            stateFog.convertIconToWeatherRecommendation()
        )
    }

    @Test
    fun convertIconToWeatherRecommendation_NULL_VALUE() {
        val state = null
        assertEquals(null, state.convertIconToWeatherRecommendation())
    }

    @Test
    fun convertIconToWeatherRecommendation_NOT_PROPER_VALUE() {
        val state = "weather"
        assertEquals(null, state.convertIconToWeatherRecommendation())
    }
}