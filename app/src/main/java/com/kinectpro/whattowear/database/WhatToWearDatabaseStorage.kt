package com.kinectpro.whattowear.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WhatToWearDatabaseStorage(private val tripDao: TripDao) : IDatabaseStorage {

    override fun saveTripToDatabase(trip: TripItem) {
        GlobalScope.launch {
            tripDao.insert(trip)
        }
    }

    override fun loadAllTripsFromDatabase(): LiveData<List<TripItem>> {
        return tripDao.getAllTrips()
    }

    override fun updateSelectedTrip(trip: TripItem) {
        GlobalScope.launch {
            tripDao.update(trip)
        }
    }

    override fun deleteSelectedTrip(trip: TripItem) {
        GlobalScope.launch {
            tripDao.delete(trip)
        }
    }

    override fun clearDatabase() {
        GlobalScope.launch {
            tripDao.deleteAll()
        }
    }

}