package com.testapp.whattowear.utils

import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.item.model.TripItem
import org.junit.Test

import org.junit.Assert.*

class PreferencesUtilsKtTest {
    private val testPlace = PlaceTrip("1", "PlaceName", "PlaceLat", "PlaceLon")
    private val jsonPlace = """{"id":"1","name":"PlaceName","latitude":"PlaceLat","longitude":"PlaceLon"}"""

    private val dateRange = listOf<Long>(1234567L, 1472589L, 9876543L)
    private val jsonDateRange = "[1234567,1472589,9876543]"

    private val kitItems = listOf(TripItem("item1"), TripItem("item1"))
    private val jsonKitItems = """[{"name":"item1"},{"name":"item1"}]"""
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