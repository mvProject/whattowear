package com.kinectpro.whattowear.database

import androidx.lifecycle.LiveData

interface IDatabaseStorage {
    fun saveTripToDatabase(trip: TripItem)
    fun loadAllTripsFromDatabase(): LiveData<List<TripItem>>
    fun updateSelectedTrip(trip: TripItem)
    fun deleteSelectedTrip(trip: TripItem)
    fun clearDatabase()
}