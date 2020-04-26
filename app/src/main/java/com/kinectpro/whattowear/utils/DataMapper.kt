package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.trip.TripWithWears
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.entity.TripEntity
import com.kinectpro.whattowear.database.entity.TripWithCheckList
import com.kinectpro.whattowear.database.entity.WearEntity

/**
 * Convert list of trip entities to list of trip models
 */
fun List<TripEntity>.convertTripEntitiesToTripModels(): List<TripItem> {
    return this.map { it.convertTripEntityToTripModel() }
}

fun TripWithCheckList.convertTripWithCheckListEntityToTripWithWearModel(): TripWithWears {
    with(this) {
        return TripWithWears(
            trip.convertTripEntityToTripModel(),
            checkList.convertWearEntitiesToWearItems()
        )
    }
}

/**
 * Convert list of wear entities to list of wear models
 */
fun List<WearEntity>.convertWearEntitiesToWearItems(): List<WearItem> {
    return this.map { it.convertWearEntityToWearItem() }
}

/**
 * Convert list of wear models to list of wear entities
 */
fun List<WearItem>.convertWearItemsToWearEntities(): List<WearEntity> {
    return this.map { it.convertWearItemToWearEntity() }
}

/**
 * Convert trip entity to trip model
 */
fun TripEntity.convertTripEntityToTripModel() = with(this) {
    TripItem(
        id,
        destinationId,
        destinationPlace,
        nightTemp,
        dayTemp,
        startDate,
        endDate
    )
}

/**
 * Convert trip model to trip entity
 */
fun TripItem.convertTripModelToTripEntity() = with(this) {
    TripEntity(
        id,
        placeId,
        place,
        nightTemp,
        dayTemp,
        startDate,
        endDate
    )
}

/**
 * Convert wear model to wear entity
 */
fun WearItem.convertWearItemToWearEntity() = with(this) {
    WearEntity(
        id,
        name,
        isChecked,
        tripId,
        type
    )
}

/**
 * Convert wear entity to wear model
 */
fun WearEntity.convertWearEntityToWearItem() = with(this) {
    WearItem(
        id,
        wearName,
        wearChecked,
        tripId,
        type
    )
}