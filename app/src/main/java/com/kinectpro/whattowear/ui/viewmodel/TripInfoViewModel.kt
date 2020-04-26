package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kinectpro.whattowear.data.model.trip.TripWithWears
import com.kinectpro.whattowear.data.model.wear.ITEM_TYPE_PERSONAL
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.ITrip
import com.kinectpro.whattowear.database.TripRepository
import com.kinectpro.whattowear.utils.convertTripWithCheckListEntityToTripWithWearModel
import com.kinectpro.whattowear.utils.filteredType

class TripInfoViewModel(application: Application, tripId: String) : AndroidViewModel(application) {

    private val tripDetailId = tripId
    private val repository: ITrip = TripRepository(getApplication(), viewModelScope)
    val tripDetail: LiveData<TripWithWears>
    val defaultListVisibility = MutableLiveData<Boolean>().apply { value = false }
    val isDefaultListNotNull = MutableLiveData<Boolean>()

    init {
        tripDetail = repository.loadSingleTripWithCheckListsFromDatabase(tripDetailId)
            .map { it.convertTripWithCheckListEntityToTripWithWearModel() }.also {
                isDefaultListNotNull.value =
                    it.value?.wears?.filteredType(ITEM_TYPE_PERSONAL)?.isNotEmpty() ?: false
            }
    }

    fun changeVisibility() {
        defaultListVisibility.value = defaultListVisibility.value != true
    }

    fun updateWears(wears: List<WearItem>) {
        repository.updateWears(wears)
    }

    fun addPersonalWear(wear: WearItem) {
        repository.saveWearToDatabase(wear)
    }

    fun editSelectedWear(wear: WearItem) {
        repository.updateSelectedWear(wear)
    }

    fun deleteSelectedWearFromDb(wear: WearItem) {
        repository.deleteSelectedWear(wear)
    }
}
