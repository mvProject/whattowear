package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.wear.model.WearItem
import com.kinectpro.whattowear.data.wear.model.WeatherTemp
import org.junit.Test

import org.junit.Assert.*

class WearUtilsTest {

    @Test
    fun convertTempToWearType_Cold() {
        assertEquals(WeatherTemp.COLD, convertTempToWearType(-19.3f))
    }

    @Test
    fun convertTempToWearType_HOT() {
        assertEquals(WeatherTemp.HOT, convertTempToWearType(12.5f))
    }

    @Test
    fun convertTempToWearType_NORMAL() {
        assertEquals(WeatherTemp.NORMAL, convertTempToWearType(5f))
    }

    @Test
    fun getWearsToSelectAccordingTempRange_SINGLE_COLD() {
        val expectedListCold = listOf(
            WearItem("jacket", WeatherTemp.COLD),
            WearItem("warmpants", WeatherTemp.COLD),
            WearItem("hat", WeatherTemp.COLD)
        )
        val dummyTemps = listOf(-20f)
        assertEquals(expectedListCold, getWearsToSelectAccordingTempRange(dummyTemps))
    }

    @Test
    fun getWearsToSelectAccordingTempRange_SINGLE_NORMAL() {
        val expectedListNormal =
            listOf(WearItem("shirt1", WeatherTemp.NORMAL), WearItem("pants", WeatherTemp.NORMAL))
        val dummyTemps = listOf(5f)
        assertEquals(expectedListNormal, getWearsToSelectAccordingTempRange(dummyTemps))
    }

    @Test
    fun getWearsToSelectAccordingTempRange_SINGLE_HOT() {
        val expectedListHOT = listOf(
            WearItem("shirt2", WeatherTemp.HOT),
            WearItem("t-shirt", WeatherTemp.HOT),
            WearItem("shorts", WeatherTemp.HOT),
            WearItem("cap", WeatherTemp.HOT)
        )
        val dummyTemps = listOf(20f)
        assertEquals(expectedListHOT, getWearsToSelectAccordingTempRange(dummyTemps))
    }

    @Test
    fun getWearsToSelectAccordingTempRange_RANGE() {
        val expectedListRange = listOf(
            WearItem("jacket", WeatherTemp.COLD),
            WearItem("warmpants", WeatherTemp.COLD),
            WearItem("hat", WeatherTemp.COLD),
            WearItem("shirt1", WeatherTemp.NORMAL),
            WearItem("pants", WeatherTemp.NORMAL),
            WearItem("shirt2", WeatherTemp.HOT),
            WearItem("t-shirt", WeatherTemp.HOT),
            WearItem("shorts", WeatherTemp.HOT),
            WearItem("cap", WeatherTemp.HOT)
        )
        val dummyTemps = listOf(-25f, -20f, 5f, 20f, 25f)
        assertEquals(expectedListRange, getWearsToSelectAccordingTempRange(dummyTemps))
    }
}