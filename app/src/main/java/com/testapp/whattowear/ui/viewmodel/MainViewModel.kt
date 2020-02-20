package com.testapp.whattowear.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip

class MainViewModel : ViewModel() {

    val selectedPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()
    val selectedTripStartDate = MutableLiveData<Long>()
    val selectedTripEndDate = MutableLiveData<Long>()


    fun getSelectedWeather(): PlaceSelectionListener {
        return object : PlaceSelectionListener {

            override fun onError(status: Status) {
                selectedPlaceStatus.value = status.statusMessage
            }

            override fun onPlaceSelected(place: Place) {
                selectedPlace.value = PlaceTrip(
                    place.id!!,
                    place.name!!,
                    place.latLng?.latitude.toString(),
                    place.latLng?.longitude.toString()
                )
            }
        }
    }
}
