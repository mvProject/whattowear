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
        endDate,
        checkList?.convertStringToWearItems()
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
        endDate,
        checkList?.convertWearItemsToStrings()
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

/**
 * Converter from wear item list single string
 * @return string value
 */
fun List<WearItem>?.convertWearItemsToStrings(): String? {
    return this?.joinToString {
        it.name
    }
}

/**
 * Converter from single string to wear item list
 * @return list of wear items in case of proper string
 * @return null in other cases
 */
fun String?.convertStringToWearItems(): List<WearItem>? {
    if ((this != null) && (this.isNotEmpty()) && (this.contains(","))) {
        val tripList = mutableListOf<WearItem>()
        for (item in tripList) {
            tripList.add(WearItem(item.name, item.isChecked, item.tripId))
        }
        return tripList
    }
    return null
}