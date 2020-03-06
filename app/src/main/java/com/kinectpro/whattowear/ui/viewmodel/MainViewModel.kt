package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import android.app.DatePickerDialog
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
                selectedDestinationPlace.value =
                    PlaceTrip(
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
                val weatherList = repository.getWeatherForecastForSelectedPlace(
                    place.latitude,
                    place.longitude,
                    it
                )
                selectedTripCondition.addSource(weatherList, Observer {
                    when (weatherList.value?.status) {
                        RequestStatus.LOADING -> {
                            selectedTripCondition.postValue(ResourceWrapper.loading())
                        }
                        RequestStatus.SUCCESS -> {
                            selectedTripCondition.postValue(
                                ResourceWrapper.success(
                                    tripCondition.getTripWeatherCondition(
                                        weatherList.value?.data!!
                                    )
                                )
                            )
                        }
                        com.kinectpro.whattowear.data.wrapper.Status.ERROR -> {

                        }
                    }
                })
                return weatherList
            }
        }
        return null
    }
}
