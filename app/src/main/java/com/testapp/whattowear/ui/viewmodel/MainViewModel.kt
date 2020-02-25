package com.testapp.whattowear.ui.viewmodel

import androidx.lifecycle.*
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.repository.RepositoryLive

class MainViewModel : ViewModel() {

    val selectedPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    fun getDestinationPlace(): PlaceSelectionListener {
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

    fun addNewCustomWear(){
        // TODO add new item feature
    }

    private val repository = RepositoryLive()

    val selectedPlaceWeatherData : LiveData<List<WeatherData>> = liveData {
        val dataRange = mutableListOf<Long>()
            dataRange.add(1582114347)
            dataRange.add(1582269744)
            dataRange.add(1582356144)
        selectedPlace.value?.let {
            emitSource(repository.getDarkSkyWeatherLiveDataForDateRange(it.latitude,it.longitude,dataRange))
        }
    }
}

