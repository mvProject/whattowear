package com.kinectpro.whattowear.data.storage

import com.kinectpro.whattowear.data.location.PlaceTrip
import com.kinectpro.whattowear.data.wear.model.item.WearItem

interface ICache {
    fun getLastSelectedPlace(): PlaceTrip?
    fun setLastSelectedPlace(selectedPlace: PlaceTrip)
    fun getLastSelectedDateRange(): List<Long>?
    fun setLastSelectedDateRange(selectedDates: List<Long>)
    fun getLastSelectedTripKit(): List<WearItem>?
    fun setLastSelectedTripKit(selectedKitItems: List<WearItem>)
}