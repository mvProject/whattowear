package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.entity.TripDatabaseModel
import com.kinectpro.whattowear.database.entity.WearDatabaseModel
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class DataMapperTest {

    private val defaultTripModel =
        TripItem(1, "placeId", "placeName", "nightTemp", "day", 123L, 456L, "tripid")
    private val defaultTripModels = listOf(defaultTripModel, defaultTripModel, defaultTripModel)

    private val defaultTripDbModel =
        TripDatabaseModel(1, "placeId", "placeName", "nightTemp", "day", 123L, 456L, "tripid")
    private val defaultTripDbModels =
        listOf(defaultTripDbModel, defaultTripDbModel, defaultTripDbModel)

    private val defaultWearItem = WearItem("test1", true, "tripid")
    private val defaultWearItems =
        listOf(defaultWearItem, defaultWearItem)

    private val defaultWearDbItem = WearDatabaseModel("test1", true, "tripid")
    private val defaultWearDbItems =
        listOf(defaultWearDbItem, defaultWearDbItem)

    private val emptyWearItems = listOf<WearItem>()
    private val emptyWearDbItems = listOf<WearDatabaseModel>()
    private val emptyTripItems = listOf<WearDatabaseModel>()

    @Test
    fun convertTripDbModelsToTripModels_Proper() {
        assertEquals(defaultTripModels, defaultTripDbModels.convertTripDbModelsToTripModels())
    }

    @Test
    fun convertTripDbModelsToTripModels_NotNull() {
        assertNotEquals(null, defaultTripDbModels.convertTripDbModelsToTripModels())
    }

    @Test
    fun convertTripDbModelsToTripModels_NotEmpty() {
        assertNotEquals(emptyTripItems, defaultTripDbModels.convertTripDbModelsToTripModels())
    }

    @Test
    fun convertWearItemDbModelsToWearItemModels_Proper() {
        assertEquals(defaultWearItems, defaultWearDbItems.convertWearItemDbModelsToWearItemModels())
    }

    @Test
    fun convertWearItemDbModelsToWearItemModels_NotNull() {
        assertNotEquals(null, defaultWearDbItems.convertWearItemDbModelsToWearItemModels())
    }

    @Test
    fun convertWearItemDbModelsToWearItemModels_NotEmpty() {
        assertNotEquals(emptyWearItems, defaultTripDbModels.convertTripDbModelsToTripModels())
    }

    @Test
    fun convertWearItemModelsToWearItemDbModels_Proper() {
        assertEquals(defaultWearDbItems, defaultWearItems.convertWearItemModelsToWearItemDbModels())
    }

    @Test
    fun convertWearItemModelsToWearItemDbModels_NotNull() {
        assertNotEquals(null, defaultWearItems.convertWearItemModelsToWearItemDbModels())
    }

    @Test
    fun convertWearItemModelsToWearItemDbModels_NotEmpty() {
        assertNotEquals(emptyWearDbItems, defaultTripDbModels.convertTripDbModelsToTripModels())
    }

    @Test
    fun convertTripDbModelToTripModel_Proper() {
        assertEquals(defaultTripModel, defaultTripDbModel.convertTripDbModelToTripModel())
    }

    @Test
    fun convertTripDbModelToTripModel_NotNull() {
        assertNotEquals(null, defaultTripDbModel.convertTripDbModelToTripModel())
    }

    @Test
    fun convertWearItemModelToWearItemDbModel_Proper() {
        assertEquals(defaultWearDbItem, defaultWearItem.convertWearItemModelToWearItemDbModel())
    }

    @Test
    fun convertWearItemModelToWearItemDbModel_NotNull() {
        assertNotEquals(null, defaultWearItem.convertWearItemModelToWearItemDbModel())
    }

    @Test
    fun convertWearItemDbModelToWearItemModel_Proper() {
        assertEquals(defaultWearItem, defaultWearDbItem.convertWearItemDbModelToWearItemModel())
    }

    @Test
    fun convertWearItemDbModelToWearItemModel_NotNull() {
        assertNotEquals(null, defaultWearDbItem.convertWearItemDbModelToWearItemModel())
    }
}