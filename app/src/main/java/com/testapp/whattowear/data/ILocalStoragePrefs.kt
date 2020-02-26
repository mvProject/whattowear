package com.testapp.whattowear.data

interface ILocalStoragePrefs {
    fun getTripKitForRange() : List<TripKit>
    fun setTripKitForRange(tripKits: List<TripKit>)
    fun getWearItemsForSelect() : List<WearItem>
    fun setWearItemsForSelect(wearItems: List<WearItem>)
    fun getTripItemsForSelect() : List<TripItem>
    fun setTripItemsForSelect(tripItems: List<TripItem>)
}