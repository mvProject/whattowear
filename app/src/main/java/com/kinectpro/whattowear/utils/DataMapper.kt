package com.kinectpro.whattowear.utils

import com.kinectpro.whattowear.data.model.TripItem
import com.kinectpro.whattowear.database.TripDatabaseModel

fun List<TripDatabaseModel>.convertDbModelsToModels(): List<TripItem> {
    val tripList = mutableListOf<TripItem>()
    for (trip in this) {
        tripList.add(trip.convertDbModelToModel())
    }
    return tripList
}

fun TripDatabaseModel.convertDbModelToModel() = with(this) {
    TripItem(destinationId, destinationPlace, startDate, endDate)
}

fun TripItem.convertModelToDbModel() = with(this) {
    TripDatabaseModel(placeId, place, startDate, endDate)
}
