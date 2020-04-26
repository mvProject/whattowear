package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.trip.TripWithWears
import com.kinectpro.whattowear.data.model.wear.ITEM_TYPE_PERSONAL
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.entity.TripEntity
import com.kinectpro.whattowear.database.entity.TripWithCheckList
import com.kinectpro.whattowear.database.entity.WearEntity
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class DataMapperTest {

    private val defaultTripModel =
        TripItem("1", "placeId", "placeName", "nightTemp", "day", 123L, 456L)
    private val defaultTripModels = listOf(defaultTripModel, defaultTripModel, defaultTripModel)

    private val defaultTripEntity =
        TripEntity("1", "placeId", "placeName", "nightTemp", "day", 123L, 456L)
    private val defaultTripEntities =
        listOf(defaultTripEntity, defaultTripEntity, defaultTripEntity)

    private val defaultWearItem = WearItem(11, "test1", true, "tripid")
    private val defaultWearItems =
        listOf(defaultWearItem, defaultWearItem)

    private val defaultWearEntity = WearEntity(11, "test1", true, "tripid", ITEM_TYPE_PERSONAL)
    private val defaultWearEntities =
        listOf(defaultWearEntity, defaultWearEntity)

    private val emptyWearItems = listOf<WearItem>()
    private val emptyWearEntities = listOf<WearEntity>()
    private val emptyTripItems = listOf<WearEntity>()

    private val tripWithWears = TripWithWears(defaultTripModel, defaultWearItems)
    private val tripWithCheckList = TripWithCheckList(defaultTripEntity, defaultWearEntities)

    @Test
    fun convertTripWithCheckListEntityToTripWithWearModel_Proper() {
        assertEquals(
            tripWithWears,
            tripWithCheckList.convertTripWithCheckListEntityToTripWithWearModel()
        )
    }
    
    @Test
    fun convertTripEntitiesToTripModels_Proper() {
        assertEquals(defaultTripModels, defaultTripEntities.convertTripEntitiesToTripModels())
    }

    @Test
    fun convertTripEntitiesToTripModels_NotNull() {
        assertNotEquals(null, defaultTripEntities.convertTripEntitiesToTripModels())
    }

    @Test
    fun convertTripEntitiesToTripModels_NotEmpty() {
        assertNotEquals(emptyTripItems, defaultTripEntities.convertTripEntitiesToTripModels())
    }

    @Test
    fun convertWearEntitiesToWearItems_Proper() {
        assertEquals(defaultWearItems, defaultWearEntities.convertWearEntitiesToWearItems())
    }

    @Test
    fun convertWearEntitiesToWearItems_NotNull() {
        assertNotEquals(null, defaultWearEntities.convertWearEntitiesToWearItems())
    }

    @Test
    fun convertWearEntitiesToWearItems_NotEmpty() {
        assertNotEquals(emptyWearItems, defaultWearEntities.convertWearEntitiesToWearItems())
    }

    @Test
    fun convertWearItemsToWearEntities_Proper() {
        assertEquals(defaultWearEntities, defaultWearItems.convertWearItemsToWearEntities())
    }

    @Test
    fun convertWearItemsToWearEntities_NotNull() {
        assertNotEquals(null, defaultWearItems.convertWearItemsToWearEntities())
    }

    @Test
    fun convertWearItemsToWearEntities_NotEmpty() {
        assertNotEquals(emptyWearEntities, defaultWearItems.convertWearItemsToWearEntities())
    }

    @Test
    fun convertTripEntityToTripModel_Proper() {
        assertEquals(defaultTripModel, defaultTripEntity.convertTripEntityToTripModel())
    }

    @Test
    fun convertTripEntityToTripModel_NotNull() {
        assertNotEquals(null, defaultTripEntity.convertTripEntityToTripModel())
    }

    @Test
    fun convertWearItemToWearEntity_Proper() {
        assertEquals(defaultWearEntity, defaultWearItem.convertWearItemToWearEntity())
    }

    @Test
    fun convertWearItemToWearEntity_NotNull() {
        assertNotEquals(null, defaultWearItem.convertWearItemToWearEntity())
    }

    @Test
    fun convertWearEntityToWearItem_Proper() {
        assertEquals(defaultWearItem, defaultWearEntity.convertWearEntityToWearItem())
    }

    @Test
    fun convertWearEntityToWearItem_NotNull() {
        assertNotEquals(null, defaultWearEntity.convertWearEntityToWearItem())
    }
}