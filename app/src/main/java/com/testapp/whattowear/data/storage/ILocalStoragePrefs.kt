package com.testapp.whattowear.data.storage

import com.testapp.whattowear.data.item.model.TripItem
import com.testapp.whattowear.data.wear.model.WearItem

interface ILocalStoragePrefs {
    fun getWearItemsForSelect(): List<WearItem>
    fun setWearItemsForSelect(wearItems: List<WearItem>)
    fun getTripItemsForSelect(): List<TripItem>
    fun setTripItemsForSelect(tripItems: List<TripItem>)
}