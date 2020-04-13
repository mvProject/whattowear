package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.location.PlaceTrip
import org.junit.Assert.*
import org.junit.Test

class PreferencesUtilsTest {
    private val testPlace = PlaceTrip(
        "1",
        "PlaceName",
        "PlaceLat",
        "PlaceLon"
    )
    private val testSecondPlace = PlaceTrip(
        "1",
        "SecondPlaceName",
        "SecondPlaceLat",
        "SecondPlaceLon"
    )
    private val jsonPlace =
        """{"id":"1","name":"PlaceName","latitude":"PlaceLat","longitude":"PlaceLon","offsetUTC":7200}"""

    private val jsonSecondPlace =
        """{"id":"1","name":"SecondPlaceName","latitude":"PlaceLat","longitude":"PlaceLon","offsetUTC":7200}"""

    private val jsonNotValid =
        """{"id":"1","name":"SecondPlaceName"}"""

    private val dateRange = listOf(1234567L, 1472589L, 9876543L)
    private val jsonDateRange = "[1234567,1472589,9876543]"

    @Test
    fun placeToJson_toJsonString() {
        assertEquals(jsonPlace, testPlace.placeToJson())
    }

    @Test
    fun placeToJson_WrongJsonString() {
        assertNotEquals(jsonSecondPlace, testPlace.placeToJson())
    }

    @Test
    fun placeToJson_NotValidJsonString() {
        assertNotEquals(jsonNotValid, testPlace.placeToJson())
    }

    @Test
    fun jsonToPlace() {
        assertEquals(testPlace, jsonPlace.jsonToPlace())
    }

    @Test
    fun jsonToPlace_WrongJson() {
        assertNotEquals(testSecondPlace, jsonNotValid.jsonToPlace())
    }

    @Test
    fun jsonToPlace_Type() {
        assertTrue(jsonPlace.jsonToPlace() is PlaceTrip)
    }

    @Test
    fun jsonToPlace_JsonNull() {
        assertNull(null.jsonToPlace())
    }

    @Test
    fun dateRangeToJson() {
        assertEquals(jsonDateRange, dateRange.dateRangeToJson())
    }

    @Test
    fun jsonToDateRange() {
        assertEquals(dateRange, jsonDateRange.jsonToDateRange())
    }

    @Test
    fun jsonToDateRange_JsonNull() {
        assertEquals(null, null.jsonToDateRange())
    }
}