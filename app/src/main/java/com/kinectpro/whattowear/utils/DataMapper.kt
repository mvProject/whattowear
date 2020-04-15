package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.TripDatabaseModel

/**
 *
 */
fun List<TripDatabaseModel>.convertDbModelsToModels(): List<TripItem> {
    val tripList = mutableListOf<TripItem>()
    for (trip in this) {
        tripList.add(trip.convertDbModelToModel())
    }
    return tripList
}

/**
 *
 */
fun TripDatabaseModel.convertDbModelToModel() = with(this) {
    TripItem(
        destinationId,
        destinationPlace,
        startDate,
        endDate,
        checkList?.convertStringToWearItems()
    )
}

/**
 *
 */
fun TripItem.convertModelToDbModel() = with(this) {
    TripDatabaseModel(placeId, place, startDate, endDate, checkList?.convertWearItemsToStrings())
}

/**
 * Converter from wear item list single string
 * @return string value
 */
private fun List<WearItem>?.convertWearItemsToStrings(): String? {
    return this?.joinToString {
        it.name
    }
}

/**
 * Converter from single string to wear item list
 * @return list of wear items
 * @return null if source string null
 */
private fun String?.convertStringToWearItems(): List<WearItem>? {
    val tripList = mutableListOf<WearItem>()
    this.let {
        val listItems = this!!.split(",")
        for (item in listItems) {
            tripList.add(WearItem(item))
        }
    }
    return tripList
}
