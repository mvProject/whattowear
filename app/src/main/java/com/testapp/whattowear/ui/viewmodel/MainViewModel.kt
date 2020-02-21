package com.testapp.whattowear.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.repository.DarkSkyRepository
import com.testapp.whattowear.repository.DarkSkyService
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    val selectedPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()
    val singleWeatherList = MutableLiveData<List<WeatherData>>()

    private var myJob: Job? = null

    private val darkSkyService = DarkSkyService()
  //  private val darkSkyRepository = DarkSkyRepository()

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

    fun getSelectedPlaceWeatherRange(placeTrip: PlaceTrip?, dataRange : MutableList<Long>){
        placeTrip?.let{
           // TODO replace  
            myJob = CoroutineScope(Dispatchers.IO).launch {
                val weatherList = darkSkyService.getDarkSkyWeatherDataForDateRange(placeTrip.latitude,placeTrip.longitude,dataRange)
                withContext(Dispatchers.Main) {
                    singleWeatherList.value = weatherList
                }
            }

        }

    }



}
