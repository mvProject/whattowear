package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kinectpro.whattowear.data.model.TripItem
import com.kinectpro.whattowear.database.WhatToWearDatabase

class TripListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WhatToWearDatabase =
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
