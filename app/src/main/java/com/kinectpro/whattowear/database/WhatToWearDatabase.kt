package com.kinectpro.whattowear.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.utils.convertDbModelsToModels
import com.kinectpro.whattowear.utils.convertModelToDbModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class WhatToWearDatabase(context: Context, private val scope: CoroutineScope) :
    IDatabase {

    private val tripDao = TripDatabase.getInstance(context).tripDatabaseDao

    override fun saveTripToDatabase(trip: TripItem) {
        scope.launch {
            tripDao.insert(trip.convertModelToDbModel())
        }
    }

    override fun loadAllTripsFromDatabase(): LiveData<List<TripItem>> {
        return tripDao.getAllTrips().map { it.convertDbModelsToModels() }

    }

    override fun updateSelectedTrip(trip: TripItem) {
        scope.launch {
            tripDao.update(trip.convertModelToDbModel())
        }
    }

    override fun deleteSelectedTrip(trip: TripItem) {
        scope.launch {
            tripDao.delete(trip.convertModelToDbModel())
        }
    }

    override fun clearDatabase() {
        scope.launch {
            tripDao.deleteAll()
        }
    }

}