package com.testapp.whattowear.data.storage

import com.testapp.whattowear.data.ITripKitItem
import com.testapp.whattowear.data.PlaceTrip

interface ILocalStoragePrefs {
    fun getLastSelectedPlace(): PlaceTrip?
    fun setLastSelectedPlace(selectedPlace: PlaceTrip)
    fun getLastSelectedDateRange(): List<Long>?
    fun setLastSelectedDateRange(selectedDates: List<Long>)
    fun getLastSelectedTripKit(): List<ITripKitItem>?
    fun setLastSelectedTripKit(selectedKitItems: List<ITripKitItem>)
}