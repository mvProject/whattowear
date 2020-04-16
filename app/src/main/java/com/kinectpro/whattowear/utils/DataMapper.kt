package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.entity.TripDatabaseModel
import com.kinectpro.whattowear.database.entity.WearDatabaseModel

/**
 * Convert list of trip database models to list of trip models
 */
fun List<TripDatabaseModel>.convertDbModelsToModels(): List<TripItem> {
    return this.map { it.convertDbModelToModel() }
}

/**
 * Convert list of wear database models to list of wear models
 */
fun List<WearDatabaseModel>.convertWearItemDbModelsToWearItemModels(): List<WearItem> {
    return this.map { it.convertWearItemDbModelToWearItemModel() }
}

/**
 * Convert list of wear models to list of wear database models
 */
fun List<WearItem>.convertWearItemModelsToWearItemDbModels(): List<WearDatabaseModel> {
    return this.map { it.convertWearItemModelToWearItemDbModel() }
}

/**
 * Convert trip database model to trip model
 */
fun TripDatabaseModel.convertDbModelToModel() = with(this) {
    TripItem(
        id,
        destinationId,
        destinationPlace,
        nightTemp,
        dayTemp,
        startDate,
        endDate,
        tripId
    )
}

/**
 * Convert trip model to trip database model
 */
fun TripItem.convertModelToDbModel() = with(this) {
    TripDatabaseModel(
        id,
        placeId,
        place,
        nightTemp,
        dayTemp,
        startDate,
        endDate,
        tripId
    )
}

/**
 * Convert wear model to wear database model
 */
fun WearItem.convertWearItemModelToWearItemDbModel() = with(this) {
    WearDatabaseModel(
        name,
        isChecked,
        tripId
    )
}

/**
 * Convert wear database model to wear model
 */
fun WearDatabaseModel.convertWearItemDbModelToWearItemModel() = with(this) {
    WearItem(
        wearName,
        wearChecked,
        wearTripId
    )
}