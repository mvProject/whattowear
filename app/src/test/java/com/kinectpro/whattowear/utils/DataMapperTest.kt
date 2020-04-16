package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.wear.WearItem
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class DataMapperTest {

    private val defaultStrings = "test1, test1"
    private val defaultWearItems = listOf(WearItem("test1"), WearItem("test1"))
    private val emptyWearItems = listOf<WearItem>()

    @Test
    fun convertStringToWearItems_Proper() {
        assertEquals(defaultWearItems, defaultStrings.convertStringToWearItems())
    }

    @Test
    fun convertStringToWearItems_NullList() {
        assertEquals(null, null.convertStringToWearItems())
    }

    @Test
    fun convertStringToWearItems_EmptyString() {
        assertEquals(null, "".convertStringToWearItems())
    }

    @Test
    fun convertStringToWearItems_Wrong() {
        assertEquals(null, "something".convertStringToWearItems())
    }

    @Test
    fun convertWearItemsToStrings_Proper() {
        assertEquals(defaultStrings, defaultWearItems.convertWearItemsToStrings())
    }

    @Test
    fun convertWearItemsToStrings_NullList() {
        assertEquals(null, null.convertWearItemsToStrings())
    }

    @Test
    fun convertWearItemsToStrings_EmptyList() {
        assertEquals("", emptyWearItems.convertWearItemsToStrings())
    }

    @Test
    fun convertWearItemsToStrings_Wrong() {
        assertNotEquals("something", defaultWearItems.convertWearItemsToStrings())
    }


}