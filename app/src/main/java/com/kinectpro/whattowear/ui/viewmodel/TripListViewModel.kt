package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kinectpro.whattowear.database.TripDatabase
import com.kinectpro.whattowear.database.TripItem
import com.kinectpro.whattowear.database.WhatToWearDatabaseStorage

class TripListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WhatToWearDatabaseStorage
    val allTrips: LiveData<List<TripItem>>

    init {
        val wordsDao = TripDatabase.getInstance(application, viewModelScope).tripDatabaseDao
        repository = WhatToWearDatabaseStorage(wordsDao)
        allTrips = repository.loadAllTripsFromDatabase()
    }

    fun add() {
        repository.saveTripToDatabase(TripItem(2, "22", "place20", 1L, 2L))
    }
}
