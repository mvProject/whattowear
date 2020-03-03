package com.testapp.whattowear.utils

import org.junit.Test

import org.junit.Assert.*

class WearUtilsTest {

    @Test
    fun convertTempToWearType_Cold() {
        assertEquals(WearType.COLD, convertTempToWearType(-19.3f))
    }

    @Test
    fun convertTempToWearType_HOT() {
        assertEquals(WearType.HOT, convertTempToWearType(12.5f))
    }

    @Test
    fun convertTempToWearType_NORMAL() {
        assertEquals(WearType.NORMAL, convertTempToWearType(5f))
    }

    @Test
    fun getWearsToSelectAccordingTempRange_SINGLE_COLD() {
        val expectedListCold = listOf(
            Wears("jacket", WearType.COLD),
            Wears("warmpants", WearType.COLD),
            Wears("hat", WearType.COLD)
        )
        val dummyTemps = listOf(-20f)
        assertEquals(expectedListCold, getWearsToSelectAccordingTempRange(dummyTemps))
    }

    @Test
    fun getWearsToSelectAccordingTempRange_SINGLE_NORMAL() {
        val expectedListNormal =
            listOf(Wears("shirt1", WearType.NORMAL), Wears("pants", WearType.NORMAL))
        val dummyTemps = listOf(5f)
        assertEquals(expectedListNormal, getWearsToSelectAccordingTempRange(dummyTemps))
    }

    @Test
    fun getWearsToSelectAccordingTempRange_SINGLE_HOT() {
        val expectedListHOT = listOf(
            Wears("shirt2", WearType.HOT),
            Wears("t-shirt", WearType.HOT),
            Wears("shorts", WearType.HOT),
            Wears("cap", WearType.HOT)
        )
        val dummyTemps = listOf(20f)
        assertEquals(expectedListHOT, getWearsToSelectAccordingTempRange(dummyTemps))
    }

    @Test
    fun getWearsToSelectAccordingTempRange_RANGE() {
        val expectedListRange = listOf(
            Wears("jacket", WearType.COLD),
            Wears("warmpants", WearType.COLD),
            Wears("hat", WearType.COLD),
            Wears("shirt1", WearType.NORMAL),
            Wears("pants", WearType.NORMAL),
            Wears("shirt2", WearType.HOT),
            Wears("t-shirt", WearType.HOT),
            Wears("shorts", WearType.HOT),
            Wears("cap", WearType.HOT)
        )
        val dummyTemps = listOf(-25f, -20f, 5f, 20f, 25f)
        assertEquals(expectedListRange, getWearsToSelectAccordingTempRange(dummyTemps))
    }
}