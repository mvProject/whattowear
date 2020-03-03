package com.testapp.whattowear.data.storage

import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.wear.model.WearItem

interface ICache {
    fun getLastSelectedPlace(): PlaceTrip?
    fun setLastSelectedPlace(selectedPlace: PlaceTrip)
    fun getLastSelectedDateRange(): List<Long>?
    fun setLastSelectedDateRange(selectedDates: List<Long>)
    fun getLastSelectedTripKit(): List<WearItem>?
    fun setLastSelectedTripKit(selectedKitItems: List<WearItem>)
}