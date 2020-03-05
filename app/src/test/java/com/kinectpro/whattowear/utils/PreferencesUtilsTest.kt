package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.data.model.wear.WeatherTemp
import org.junit.Test

import org.junit.Assert.*

class PreferencesUtilsTest {
    private val testPlace = PlaceTrip(
        "1",
        "PlaceName",
        "PlaceLat",
        "PlaceLon"
    )
    private val jsonPlace =
        """{"id":"1","name":"PlaceName","latitude":"PlaceLat","longitude":"PlaceLon"}"""

    private val dateRange = listOf<Long>(1234567L, 1472589L, 9876543L)
    private val jsonDateRange = "[1234567,1472589,9876543]"

    private val kitItems = listOf(
        WearItem(
            "item1",
            WeatherTemp.FRESH
        ), WearItem(
            "item1",
            WeatherTemp.NORMAL
        )
    )
    private val jsonKitItems =
        """[{"name":"item1","temp":"FRESH"},{"name":"item1","temp":"NORMAL"}]"""

    @Test
    fun placeToJson_toJsonString() {
        assertEquals(jsonPlace, testPlace.placeToJson())
    }

    @Test
    fun jsonToPlace() {
        assertEquals(testPlace, jsonPlace.jsonToPlace())
    }

    @Test
    fun jsonToPlace_JsonNull() {
        assertEquals(null, null.jsonToPlace())
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

    @Test
    fun kitListToJson() {
        assertEquals(jsonKitItems, kitItems.kitListToJson())
    }

    @Test
    fun jsonToKitList() {
        assertEquals(kitItems, jsonKitItems.jsonToKitList())
    }

    @Test
    fun jsonToKitList_JsonNull() {
        assertEquals(null, null.jsonToKitList())
    }
}