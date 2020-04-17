package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.database.ITrip
import com.kinectpro.whattowear.database.TripRepository

class TripListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ITrip =
        TripRepository(getApplication(), viewModelScope)

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
