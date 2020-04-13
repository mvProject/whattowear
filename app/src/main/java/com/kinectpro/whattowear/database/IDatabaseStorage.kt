package com.kinectpro.whattowear.database

interface IDatabaseStorage {
    fun saveTripToDatabase(trip: TripItem)
    fun loadTripFromDatabase(id: Int)
    fun loadAllTripsFromDatabase()
    fun updateSelectedTrip()
    fun clearDatabase()
}