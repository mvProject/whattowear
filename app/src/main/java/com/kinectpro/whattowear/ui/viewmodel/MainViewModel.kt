package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.kinectpro.whattowear.data.IWeatherRangeSummary
import com.kinectpro.whattowear.data.TripWeatherCondition
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.utils.getDataRangeForTrip
import com.kinectpro.whattowear.data.wrapper.Status as RequestStatus
import java.util.*
import com.kinectpro.whattowear.repository.WhatToWearRepository
import java.lang.Error
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WhatToWearRepository()
    private val tripCondition: IWeatherRangeSummary = TripWeatherCondition()

    val selectedDestinationPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    val selectedTripCondition = MediatorLiveData<ResourceWrapper<TripModel>>()

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
                if (place.id != null) {
                    selectedDestinationPlace.value =
                        PlaceTrip(
                            place.id!!,
                            place.name!!,
                            place.latLng?.latitude.toString(),
                            place.latLng?.longitude.toString(),
                            TimeUnit.MINUTES.toMillis(place.utcOffsetMinutes!!.toLong())
                        )
                } else {
                    Log.d("Wear", "cleared")
                }
            }
        }
    }

    var tripStartDateSelectionDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            tripStartDateLive.value = calendar.timeInMillis
            tripEndDateLive.value = calendar.timeInMillis
        }

    var tripEndDateSelectionDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            tripEndDateLive.value = calendar.timeInMillis
        }
/*
    fun addNewCustomWear() {
        // TODO commented in UncompleteUI feature,uncomment when functionality will be performed
    }

 */

    fun getSelectedPlaceWeatherData(): LiveData<ResourceWrapper<List<WeatherData>>>? {
        selectedDestinationPlace.value?.let { place ->
            getDataRangeForTrip(
                tripStartDateLive.value!! + place.offsetUTC,
                tripEndDateLive.value!! + place.offsetUTC
            )?.let {
                return repository.getWeatherForecastForSelectedPlace(
                    place.latitude,
                    place.longitude,
                    it
                )
            }
        }
        return null
    }

    fun convertWeatherListToWeatherCondition(weatherList: LiveData<ResourceWrapper<List<WeatherData>>>?) {
        if (weatherList != null) {
            selectedTripCondition.addSource(weatherList, Observer {
                when (it.status) {
                    RequestStatus.LOADING -> {
                        selectedTripCondition.value = ResourceWrapper.loading()
                    }
                    RequestStatus.SUCCESS -> {
                        selectedTripCondition.value = ResourceWrapper.success(
                            tripCondition.getTripWeatherCondition(
                                it.data!!
                            )
                        )
                    }
                    RequestStatus.ERROR -> {
                        selectedTripCondition.value = ResourceWrapper.error(Error())
                    }
                }
            })
        }
    }

}
