package com.testapp.whattowear.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip

class MainViewModel : ViewModel() {

    val selectedPlace = MutableLiveData<PlaceTrip>()


    fun getSelectedWeather(context: Context): PlaceSelectionListener {
        return object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Toast.makeText(context, status.statusMessage, Toast.LENGTH_SHORT).show()
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
