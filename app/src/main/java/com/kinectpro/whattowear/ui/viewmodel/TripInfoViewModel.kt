package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kinectpro.whattowear.database.ITrip
import com.kinectpro.whattowear.database.TripRepository

class TripInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ITrip =
        TripRepository(getApplication(), viewModelScope)

    fun loadSingleTrip(tripId: String) {

    }
}
