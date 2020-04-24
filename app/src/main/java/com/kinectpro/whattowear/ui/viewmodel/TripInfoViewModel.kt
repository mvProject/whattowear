package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kinectpro.whattowear.data.model.trip.TripWithWears
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.ITrip
import com.kinectpro.whattowear.database.TripRepository
import com.kinectpro.whattowear.utils.convertTripWithCheckListEntityToTripWithWearModel

class TripInfoViewModel(application: Application, tripId: String) : AndroidViewModel(application) {

    private val tripDetailId = tripId
    private val repository: ITrip = TripRepository(getApplication(), viewModelScope)
    val tripDetail: LiveData<TripWithWears>
    val defaultListVisibility = MutableLiveData<Boolean>().apply { value = true }

    init {
        tripDetail = repository.loadSingleTripWithCheckListsFromDatabase(tripDetailId)
            .map { it.convertTripWithCheckListEntityToTripWithWearModel() }
    }

    fun changeVisibility() {
        defaultListVisibility.value = defaultListVisibility.value != true
    }

    fun updateWears(wears: List<WearItem>) {
        repository.updateWears(wears)
    }
}
