package com.testapp.whattowear.utils

import org.junit.Test

import org.junit.Assert.*

class DataRangeUtilsTest {
    private val testStartNull = null
    private val testStartZero = 0L
    private val testStartDate = 1582704449000L
    private val testEndNull = null
    private val testEndZero = 0L
    private val testEndDate = 1582963649000L
    @Test
    fun getDataRangeForTrip_Is_Not_Null() {
        val result = getDataRangeForTrip(testStartDate, testEndDate)
        assertNotEquals(null, result)
    }

    @Test
    fun getDataRangeForTrip_Is_Null() {
        val result = getDataRangeForTrip(testEndDate, testStartDate)
        assertEquals(null, result)
    }

    @Test
    fun getDataRangeForTrip_With_Zero_Params() {
        val result = getDataRangeForTrip(0L, 0L)
        assertEquals(null, result)
    }

    @Test
    fun getDataRangeForTrip_Expected_List() {
        val expected = listOf<Long>(1582704449, 1582790849, 1582877249, 1582963649)
        val result = getDataRangeForTrip(testStartDate, testEndDate)
        assertEquals(expected, result)
    }

    @Test
    fun isProperDataRangeSelected_StartDate_Null() {
        val result = isProperDataRangeSelected(testStartNull,testEndDate)
        assertEquals(false,result)
    }

    @Test
    fun isProperDataRangeSelected_EndDate_Null() {
        val result = isProperDataRangeSelected(testStartDate,testEndNull)
        assertEquals(false,result)
    }

    @Test
    fun isProperDataRangeSelected_BothDate_Null() {
        val result = isProperDataRangeSelected(testStartNull,testEndNull)
        assertEquals(false,result)
    }

    @Test
    fun isProperDataRangeSelected_StartDate_Zero() {
        val result = isProperDataRangeSelected(testStartZero,testEndDate)
        assertEquals(false,result)
    }

    @Test
    fun isProperDataRangeSelected_End_Zero() {
        val result = isProperDataRangeSelected(testStartDate,testEndZero)
        assertEquals(false,result)
    }

    @Test
    fun isProperDataRangeSelected_BothDate_Zero() {
        val result = isProperDataRangeSelected(testStartZero,testEndZero)
        assertEquals(false,result)
    }
    @Test
    fun isProperDataRangeSelected_EndDateEarlier() {
        val result = isProperDataRangeSelected(testEndDate,testStartZero)
        assertEquals(false,result)
    }
    @Test
    fun isProperDataRangeSelected_Proper() {
        val result = isProperDataRangeSelected(testStartZero,testEndDate)
        assertEquals(false,result)
    }
}