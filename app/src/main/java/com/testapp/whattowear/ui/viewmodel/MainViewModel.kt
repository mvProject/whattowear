package com.testapp.whattowear.ui.viewmodel

import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    val selectedPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    var tripStartDate = 0L
    var tripEndDate = 0L

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

    // TODO move to utils later
    fun getTripDataRange(startDate : Long,endDate : Long) : List<Long>? {
        if((startDate>0) and (endDate>0) and (endDate>startDate)){
            val dataRange = mutableListOf<Long>()
            for (item in startDate..endDate step DateUtils.DAY_IN_MILLIS)
                dataRange.add(TimeUnit.MILLISECONDS.toSeconds(item))
            return dataRange
        }
        return null
    }
}
