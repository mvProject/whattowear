package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.kinectpro.whattowear.data.PlaceTrip
import com.kinectpro.whattowear.utils.getDataRangeForTrip
import java.util.*
import com.kinectpro.whattowear.data.WeatherData
import com.kinectpro.whattowear.data.ResourceWrapper
import com.kinectpro.whattowear.repository.DarkSkyWeatherRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DarkSkyWeatherRepository()
    val selectedDestinationPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    val tripEndDateLive = MutableLiveData<Long>().apply {
        value = 0L
    }
    val tripStartDateLive = MutableLiveData<Long>().apply {
        value = 0L
    }

    fun getTripDestinationPlaceSelected(): PlaceSelectionListener {
        return object : PlaceSelectionListener {

            override fun onError(status: Status) {
                if (!status.statusMessage.isNullOrEmpty())
                    selectedPlaceStatus.value = status.statusMessage
            }

            override fun onPlaceSelected(place: Place) {
                selectedDestinationPlace.value = PlaceTrip(
                        place.id!!,
                        place.name!!,
                        place.latLng?.latitude.toString(),
                        place.latLng?.longitude.toString()
                )
            }
        }
    }

    var tripStartDateSelectionDialogListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                tripStartDateLive.value = calendar.timeInMillis
            }

    var tripEndDateSelectionDialogListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                tripEndDateLive.value = calendar.timeInMillis
            }

    fun addNewCustomWear() {
        // TODO add new item feature
    }

    fun getSelectedPlaceWeatherData(): LiveData<ResourceWrapper<List<WeatherData>>>? {
        selectedDestinationPlace.value?.let { place ->
            getDataRangeForTrip(tripStartDateLive.value!!, tripEndDateLive.value!!)?.let {
                return repository.getDarkSkyWeatherLiveDataForDateRange(
                        place.latitude,
                        place.longitude,
                        it
                )
            }
        }
        return null
    }
}