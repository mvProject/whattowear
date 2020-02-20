package com.testapp.whattowear.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.network.WeatherRepository
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    val selectedPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()
    val singleWeatherList = MutableLiveData<MutableList<WeatherData>>()

    private var myJob: Job? = null


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

    fun getSelectedPlaceWeatherRange(placeTrip: PlaceTrip, dataRange : MutableList<Long>){
        myJob = CoroutineScope(Dispatchers.IO).launch {
            val weatherList = WeatherRepository().getDateRangeWeatherData(placeTrip.latitude,placeTrip.longitude,dataRange)
            withContext(Dispatchers.Main) {
                singleWeatherList.value = weatherList
            }
        }
    }
}
