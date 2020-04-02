package com.kinectpro.whattowear.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class DataRangeUtilsTest {
    private val testStartNull = null
    private val testStartZero = 0L
    private val testStartDate = 1583056884000L //01.03.2020
    private val testEndDate = 1583229684000L //03.03.2020
    private val testEndDateLongRange = 1593770484000L //03.07.2020
    private val testEndNull = null
    private val testEndZero = 0L
    private val dateErrorNull = 1
    private val dateErrorInvalidRangeLength = 2
    private val dateErrorStartDateZero = 3

    @Test
    fun getDataRangeForTrip_Is_Not_Null() {
        val result = getDataRangeForTrip(testStartDate, testEndDate)
        assertNotEquals(null, result)
    }

    @Test
    fun getDataRangeForTrip_Is_Null() {
        val result = getDataRangeForTrip(testEndDate, testStartDate)
        assertEquals(listOf<Long>(), result)
    }

    @Test
    fun getDataRangeForTrip_With_Zero_Params() {
        val result = getDataRangeForTrip(0L, 0L)
        assertEquals(null, result)
    }

    @Test
    fun getDataRangeForTrip_Expected_List() {
        val expected = listOf<Long>(1583056884, 1583143284, 1583229684)
        val result = getDataRangeForTrip(testStartDate, testEndDate)
        assertEquals(expected, result)
    }

    @Test
    fun isProperDataRangeSelected_StartDate_Null() {
        val result = isProperDataRangeSelected(testStartNull, testEndDate)
        assertEquals(dateErrorNull, result)
    }

    @Test
    fun isProperDataRangeSelected_EndDate_Null() {
        val result = isProperDataRangeSelected(testStartDate, testEndNull)
        assertEquals(dateErrorNull, result)
    }

    @Test
    fun isProperDataRangeSelected_BothDate_Null() {
        val result = isProperDataRangeSelected(testStartNull, testEndNull)
        assertEquals(dateErrorNull, result)
    }

    @Test
    fun isProperDataRangeSelected_StartDate_Zero() {
        val result = isProperDataRangeSelected(testStartZero, testEndDate)
        assertEquals(dateErrorStartDateZero, result)
    }

    @Test
    fun isProperDataRangeSelected_End_Zero() {
        val result = isProperDataRangeSelected(testStartDate, testEndZero)
        assertEquals(null, result)
    }

    @Test
    fun isProperDataRangeSelected_BothDate_Zero() {
        val result = isProperDataRangeSelected(testStartZero, testEndZero)
        assertEquals(dateErrorStartDateZero, result)
    }

    @Test
    fun isProperDataRangeSelected_EndDateEarlier() {
        val result = isProperDataRangeSelected(testEndDate, testStartDate)
        assertEquals(null, result)
    }

    @Test
    fun isProperDataRangeSelected_Proper() {
        val result = isProperDataRangeSelected(testStartDate, testEndDate)
        assertEquals(null, result)
    }

    @Test
    fun isProperDataRangeSelected_Length_Error() {
        val result = isProperDataRangeSelected(testStartDate, testEndDateLongRange)
        assertEquals(dateErrorInvalidRangeLength, result)
    }

    @Test
    fun isProperDataRangeSelected_Length_Proper() {
        val result = isProperDataRangeSelected(testStartDate, testEndDate)
        assertEquals(null, result)
    }

    @Test
    fun isDaysAreSame_Proper() {
        val date = 1583056884000L
        val result = isDaysAreSame(date, date)
        assertEquals(true, result)
    }

    @Test
    fun isDaysAreSame_StartDateGreater() {
        val start = 1583229684000L //03.03.2020
        val end = 1583056884000L //01.03.2020
        val result = isDaysAreSame(start, end)
        assertEquals(false, result)
    }

    @Test
    fun isDaysAreSame_EndDateGreater() {
        val start = 1583056884000L //01.03.2020
        val end = 1583229684000L //03.03.2020
        val result = isDaysAreSame(start, end)
        assertEquals(false, result)
    }

    @Test
    fun isDaysAreSame_OneDayEndHourGreater() {
        val start = 1585811281000L //02.04.2020 7h
        val end = 1585829281000L //02.04.2020 12h
        val result = isDaysAreSame(start, end)
        assertEquals(true, result)
    }

    @Test
    fun isDaysAreSame_OneDayStartHourGreater() {
        val start = 1585829281000L //02.04.2020 12h
        val end = 1585811281000L //02.04.2020 7h
        val result = isDaysAreSame(start, end)
        assertEquals(true, result)
    }

    @Test
    fun isStartHourGreaterThenEndHour_DifferentDaysStartHourGreater() {
        val start = 1585824269000L //02.04.2020 11h
        val end = 1585903469000L //02.04.2020 9h
        val result = isStartHourGreaterThenEndHour(start, end)
        assertEquals(false, result)
    }

    @Test
    fun isStartHourGreaterThenEndHour_DifferentDaysEndHourGreater() {
        val start = 1585903469000L //03.04.2020 9h
        val end = 1585824269000L //02.04.2020 11h
        val result = isStartHourGreaterThenEndHour(start, end)
        assertEquals(true, result)
    }

    @Test
    fun isStartHourGreaterThenEndHour_OneDayStartHourGreater() {
        val start = 1585829281000L //02.04.2020 12h
        val end = 1585811281000L //02.04.2020 7h
        val result = isStartHourGreaterThenEndHour(start, end)
        assertEquals(false, result)
    }

    @Test
    fun isStartHourGreaterThenEndHour_OneDayEndHourGreater() {
        val start = 1585811281000L //02.04.2020 7h
        val end = 1585829281000L //02.04.2020 12h
        val result = isStartHourGreaterThenEndHour(start, end)
        assertEquals(true, result)
    }
}