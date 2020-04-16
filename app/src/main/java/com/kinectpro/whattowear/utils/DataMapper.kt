package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.TripDatabaseModel

/**
 * Convert list of database models to list of trip models
 */
fun List<TripDatabaseModel>.convertDbModelsToModels(): List<TripItem> {
    val tripList = mutableListOf<TripItem>()
    for (trip in this) {
        tripList.add(trip.convertDbModelToModel())
    }
    return tripList
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
        val listItems = this.split(",")
        for (item in listItems) {
            tripList.add(WearItem(item.trim()))
        }
        return tripList
    }
    return null
}