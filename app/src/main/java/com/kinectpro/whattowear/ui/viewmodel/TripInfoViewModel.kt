package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kinectpro.whattowear.data.model.trip.TripWithWears
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.ITrip
import com.kinectpro.whattowear.database.TripRepository
import com.kinectpro.whattowear.utils.convertTripWithCheckListEntityToTripWithWearModel
import com.kinectpro.whattowear.utils.filteredDefaultType
import kotlin.random.Random

class TripInfoViewModel(application: Application, tripId: String) : AndroidViewModel(application) {

    private val tripDetailId = tripId
    private val repository: ITrip = TripRepository(getApplication(), viewModelScope)
    val tripDetailInformation: LiveData<TripWithWears>

    val isHasDefaultChecklist = MutableLiveData<Boolean>().apply { value = false }
    val defaultListVisibility = MutableLiveData<Boolean>().apply { value = false }
    var wearForEdit: WearItem? = null

    init {
        tripDetailInformation = repository.loadSingleTripWithCheckListsFromDatabase(tripDetailId)
            .map { it.convertTripWithCheckListEntityToTripWithWearModel() }
    }

    val defaultCheckList = MediatorLiveData<LiveData<TripWithWears>>().apply {
        addSource(tripDetailInformation) {
            isHasDefaultChecklist.value = !it.wears.filteredDefaultType(true).isNullOrEmpty()
        }
    }

    fun changeVisibility() {
        defaultListVisibility.value = defaultListVisibility.value != true
    }

    fun updateWears() {
        tripDetailInformation.value?.let {
            repository.updateWears(it.wears)
        }
    }

    fun addPersonalWear(name: String) {
        repository.saveWearToDatabase(WearItem(Random.nextInt(), name, false, tripDetailId))
    }

    fun editPersonalWear(name: String) {
        wearForEdit?.let {
            repository.updateSelectedWear(it.copy(name = name))
            wearForEdit = null
        }
    }

    fun deleteSelectedWearFromDb(wear: WearItem) {
        repository.deleteSelectedWear(wear)
    }
}
