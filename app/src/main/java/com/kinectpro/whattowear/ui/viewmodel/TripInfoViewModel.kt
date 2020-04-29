package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kinectpro.whattowear.data.model.trip.TripWithWears
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.ITrip
import com.kinectpro.whattowear.database.TripRepository
import com.kinectpro.whattowear.utils.convertTripWithCheckListEntityToTripWithWearModel
import com.kinectpro.whattowear.utils.filteredType
import java.util.*

class TripInfoViewModel(application: Application, tripId: String) : AndroidViewModel(application) {

    private val tripDetailId = tripId
    private val repository: ITrip = TripRepository(getApplication(), viewModelScope)
    val tripDetailInformation: LiveData<TripWithWears>

    var wearItemForEdit: WearItem? = null
    val isHasDefaultChecklist = MutableLiveData<Boolean>().apply { value = false }
    val defaultListVisibility = MutableLiveData<Boolean>().apply { value = false }

    init {
        tripDetailInformation = repository.loadSingleTripWithCheckListsFromDatabase(tripDetailId)
            .map { it.convertTripWithCheckListEntityToTripWithWearModel() }
    }

    val defaultCheckList = MediatorLiveData<LiveData<TripWithWears>>().apply {
        addSource(tripDetailInformation) {
            isHasDefaultChecklist.value = !it.wears.filteredType(true).isNullOrEmpty()
        }
    }

    fun changeVisibility() {
        defaultListVisibility.value = defaultListVisibility.value != true
    }

    fun updateWears() {
        repository.updateWears(tripDetailInformation.value?.wears!!)
    }

    fun addOrEditPersonalWear(name: String) {
        when (wearItemForEdit) {
            null -> {
                tripDetailInformation.value?.trip?.id.let {
                    repository.saveWearToDatabase(
                        WearItem(
                            Random().nextInt(),
                            name,
                            tripId = it
                        )
                    )
                }
            }
            else -> {
                repository.updateSelectedWear(wearItemForEdit!!.copy(name = name))
                wearItemForEdit = null
            }
        }


    }

    fun deleteSelectedWearFromDb(wear: WearItem) {
        repository.deleteSelectedWear(wear)
    }
}
