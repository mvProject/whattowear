package com.kinectpro.whattowear.database

import androidx.lifecycle.LiveData
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.entity.TripWithCheckList

interface ITrip {
    fun saveTripToDatabase(trip: TripItem, isDefaultListChecked: Boolean)
    fun loadAllTripsFromDatabase(): LiveData<List<TripItem>>
    fun loadAllTripsWithCheckListsFromDatabase(): LiveData<List<TripWithCheckList>>
    fun loadSingleTripWithCheckListsFromDatabase(tripId: String): LiveData<TripWithCheckList>
    fun updateSelectedTrip(trip: TripItem)
    fun deleteSelectedTrip(trip: TripItem)
    fun saveWearToDatabase(wear: WearItem)
    fun updateSelectedWear(wear: WearItem)
    fun updateWears(wears: List<WearItem>)
    fun deleteSelectedWear(wear: WearItem)
    fun clearDatabase()
}