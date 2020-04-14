package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kinectpro.whattowear.database.IDatabaseStorage
import com.kinectpro.whattowear.database.TripItem
import com.kinectpro.whattowear.database.WhatToWearDatabaseStorage

class TripListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: IDatabaseStorage =
        WhatToWearDatabaseStorage(getApplication(), viewModelScope)

    val allTrips: LiveData<List<TripItem>>

    init {
        allTrips = repository.loadAllTripsFromDatabase()
    }

    fun add(trip: TripItem) {
        repository.saveTripToDatabase(trip)
    }

    fun edit(trip: TripItem) {
        repository.updateSelectedTrip(trip)
    }

    fun delete(trip: TripItem) {
        repository.deleteSelectedTrip(trip)
    }

    fun deleteAll() {
        repository.clearDatabase()
    }
}
