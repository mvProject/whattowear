package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.IDatabase
import com.kinectpro.whattowear.database.IWear
import com.kinectpro.whattowear.database.WearRepository
import com.kinectpro.whattowear.database.WhatToWearDatabase

class TripListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: IDatabase =
        WhatToWearDatabase(getApplication(), viewModelScope)

    val allTrips: LiveData<List<TripItem>>

    init {
        allTrips = repository.loadAllTripsFromDatabase()
    }

    fun editSelectedTrip(trip: TripItem) {
        repository.updateSelectedTrip(trip)
    }

    fun deleteSelectedTripFromDb(trip: TripItem) {
        repository.deleteSelectedTrip(trip)
    }
}
