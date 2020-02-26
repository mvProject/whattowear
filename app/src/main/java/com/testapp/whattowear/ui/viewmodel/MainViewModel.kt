package com.testapp.whattowear.ui.viewmodel

import androidx.lifecycle.*
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.repository.DarkSkyWeatherRepository

class MainViewModel : ViewModel() {

    val selectedPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    private val dataRange = mutableListOf<Long>().apply {
        add(1582114347)
        add(1582269744)
        add(1582356144)
    }

    fun getDestinationPlace(): PlaceSelectionListener {
        return object : PlaceSelectionListener {

            override fun onError(status: Status) {
                if (!status.statusMessage.isNullOrEmpty())
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

    fun addNewCustomWear(){
        // TODO add new item feature
    }

    private val repository = DarkSkyWeatherRepository()

    fun getSelectedPlaceWeatherData() : LiveData<List<WeatherData>>? {
        selectedPlace.value?.let {
            return repository.getDarkSkyWeatherLiveDataForDateRange(it.latitude,it.longitude,dataRange)
        }
        return null
    }

}

