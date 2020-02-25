package com.testapp.whattowear.utils

import org.junit.Test

import org.junit.Assert.*

class DataRangeUtilsTest {
    private val testStartDate = 1582641234L
    private val testEndDate = 1582986834L
    @Test
    fun getDataRangeForTrip_Is_Not_Null() {
        val result = getDataRangeForTrip(testStartDate,testEndDate)
        assertNotEquals(null,result)
    }
    @Test
    fun getDataRangeForTrip_Is_Null() {
        val result = getDataRangeForTrip(testEndDate,testStartDate)
        assertEquals(null,result)
    }
    @Test
    fun getDataRangeForTrip_With_Zero_Params() {
        val result = getDataRangeForTrip(0L,0L)
        assertEquals(null,result)
    }
}