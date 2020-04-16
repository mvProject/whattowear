package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.entity.TripDatabaseModel
import com.kinectpro.whattowear.database.entity.WearDatabaseModel

/**
 * Convert list of database models to list of trip models
 */
fun List<TripDatabaseModel>.convertDbModelsToModels(): List<TripItem> {
    return this.map { it.convertDbModelToModel() }
}

/**
 *
 */
fun List<WearDatabaseModel>.convertWearItemDbModelsToWearItemModels(): List<WearItem> {
    return this.map { it.convertWearItemDbModelToWearItemModel() }
}

/**
 *
 */
fun List<WearItem>.convertWearItemModelsToWearItemDbModels(): List<WearDatabaseModel> {
    return this.map { it.convertWearItemModelToWearItemDbModel() }
}

/**
 * Convert database model to trip model
 */
fun TripDatabaseModel.convertDbModelToModel() = with(this) {
    TripItem(
        tripId,
        destinationId,
        destinationPlace,
        nightTemp,
        dayTemp,
        startDate,
        endDate
    )
}

/**
 * Convert trip model to database model
 */
fun TripItem.convertModelToDbModel() = with(this) {
    TripDatabaseModel(
        tripId,
        placeId,
        place,
        nightTemp,
        dayTemp,
        startDate,
        endDate
    )
}

/**
 *
 */
fun WearItem.convertWearItemModelToWearItemDbModel() = with(this) {
    WearDatabaseModel(
        name,
        isChecked,
        tripId
    )
}

/**
 *
 */
fun WearDatabaseModel.convertWearItemDbModelToWearItemModel() = with(this) {
    WearItem(
        wearName,
        wearChecked,
        wearTripId
    )
}