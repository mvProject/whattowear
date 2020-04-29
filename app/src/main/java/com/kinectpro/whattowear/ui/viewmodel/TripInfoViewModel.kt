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
    val tripDetail: LiveData<TripWithWears>
    val defaultListVisibility = MutableLiveData<Boolean>().apply { value = false }
    var editWear: WearItem? = null

    init {
        tripDetail = repository.loadSingleTripWithCheckListsFromDatabase(tripDetailId)
            .map { it.convertTripWithCheckListEntityToTripWithWearModel() }

    }

    fun isDefaultListEmpty(): Boolean {
        return tripDetail.value?.wears?.filteredType(true).isNullOrEmpty()
    }

    fun changeVisibility() {
        defaultListVisibility.value = defaultListVisibility.value != true
    }

    fun updateWears(wears: List<WearItem>) {
        repository.updateWears(wears)
    }

    fun addOrEditPersonalWear(name: String) {
        when (editWear) {
            null -> {
                tripDetail.value?.trip?.id.let {
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
                repository.updateSelectedWear(editWear!!.copy(name = name))
                editWear = null
            }
        }


    }

    fun deleteSelectedWearFromDb(wear: WearItem) {
        repository.deleteSelectedWear(wear)
    }
}
