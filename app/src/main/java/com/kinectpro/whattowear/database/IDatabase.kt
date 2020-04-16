package com.kinectpro.whattowear.database

import androidx.lifecycle.LiveData
import com.kinectpro.whattowear.data.model.trip.TripItem

interface IDatabase {
    fun saveTripToDatabase(trip: TripItem)
    fun loadAllTripsFromDatabase(): LiveData<List<TripItem>>
    fun updateSelectedTrip(trip: TripItem)
    fun deleteSelectedTrip(trip: TripItem)
    fun clearDatabase()
}