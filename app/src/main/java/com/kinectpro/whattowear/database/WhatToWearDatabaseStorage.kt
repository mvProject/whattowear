package com.kinectpro.whattowear.database

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class WhatToWearDatabaseStorage(context: Context, private val scope: CoroutineScope) :
    IDatabaseStorage {

    private val tripDao = TripDatabase.getInstance(context, scope).tripDatabaseDao

    override fun saveTripToDatabase(trip: TripItem) {
        scope.launch {
            tripDao.insert(trip)
        }
    }

    override fun loadAllTripsFromDatabase(): LiveData<List<TripItem>> {
        return tripDao.getAllTrips()
    }

    override fun updateSelectedTrip(trip: TripItem) {
        scope.launch {
            tripDao.update(trip)
        }
    }

    override fun deleteSelectedTrip(trip: TripItem) {
        scope.launch {
            tripDao.delete(trip)
        }
    }

    override fun clearDatabase() {
        scope.launch {
            tripDao.deleteAll()
        }
    }

}