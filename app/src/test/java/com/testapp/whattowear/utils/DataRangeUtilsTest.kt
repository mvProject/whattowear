package com.testapp.whattowear.utils

import org.junit.Test

import org.junit.Assert.*

class DataRangeUtilsTest {
    private val testStartDate = 1582704449000L
    private val testEndDate = 1582963649000L
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
//
    @Test
    fun getDataRangeForTrip_Expected_List() {
        val expected = listOf<Long>(1582704449,1582790849,1582877249,1582963649)
        val result = getDataRangeForTrip(testStartDate,testEndDate)
        assertEquals(expected,result)
}
}