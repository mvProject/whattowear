package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.kinectpro.whattowear.data.IWeatherRangeSummary
import com.kinectpro.whattowear.data.TripWeatherCondition
import com.kinectpro.whattowear.data.model.enums.ErrorCodes
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.data.storage.WhatToWearCache
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper
import com.kinectpro.whattowear.repository.WhatToWearRepository
import com.kinectpro.whattowear.utils.*
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.TimeUnit
import com.kinectpro.whattowear.data.model.enums.ResourceStatus as RequestStatus

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        WhatToWearRepository(NetworkChecker(getApplication()), WhatToWearCache(getApplication()))
    private val tripCondition: IWeatherRangeSummary = TripWeatherCondition()

    val selectedDestinationPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    val selectedTripCondition = MediatorLiveData<ResourceWrapper<TripModel>>()

    val tripRangeEndDateValue = MutableLiveData<Long>().apply {
        value = 0L
    }
    val tripRangeStartDateValue = MutableLiveData<Long>().apply {
        value = 0L
    }

    /*
     Obtain forecast when destination or start or end date change
     */
    val selectedTrip = MediatorLiveData<Long>().apply {
        addSource(selectedDestinationPlace) {
            it?.let {
                obtainSelectedDestinationWeatherRequest()
            }
        }

        addSource(tripRangeStartDateValue) {
            it?.let {
                obtainSelectedDestinationWeatherRequest()
            }
        }

        addSource(tripRangeEndDateValue) {
            it?.let {
                obtainSelectedDestinationWeatherRequest()
            }
        }
    }

    init {
        repository.getLastSelectedPlace().let {
            selectedDestinationPlace.value = it
        }
    }

    fun getTripDestinationPlaceSelected(): PlaceSelectionListener {
        return object : PlaceSelectionListener {

            override fun onError(status: Status) {
                if (!status.statusMessage.isNullOrEmpty())
                    selectedPlaceStatus.value = status.statusMessage
            }

            override fun onPlaceSelected(place: Place) {
                if ((place.id != null) && (place.name != null)) {
                    selectedDestinationPlace.value = PlaceTrip(
                        place.id!!,
                        place.name!!,
                        place.latLng?.latitude.toString(),
                        place.latLng?.longitude.toString(),
                        TimeUnit.MINUTES.toMillis(place.utcOffsetMinutes!!.toLong())
                    )
                }
            }
        }
    }

    /*
     Set selected date value as trip range start date
     */
    var tripStartDateSelectionDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            val currentDate = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            try {
                if (!isDaysAreSame(currentDate.timeInMillis, calendar.timeInMillis)) {
                    calendar.set(
                        year,
                        month,
                        dayOfMonth,
                        START_DATE_HOURS_DEFAULT,
                        START_DATE_MINUTES_DEFAULT,
                        START_DATE_SECONDS_DEFAULT
                    )
                }
                tripRangeStartDateValue.value = calendar.timeInMillis
            } catch (ex: IllegalArgumentException) {
                ex.printStackTrace()
            }
        }

    /*
     Set selected date value as trip range end date
     */
    var tripEndDateSelectionDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(
                year, month, dayOfMonth,
                END_DATE_HOURS_DEFAULT, END_DATE_MINUTES_DEFAULT, END_DATE_SECONDS_DEFAULT
            )
            tripRangeEndDateValue.value = calendar.timeInMillis
        }

    /*
     Obtain weather forecast for selected conditions
     */
    private fun getSelectedPlaceWeatherData(): LiveData<ResourceWrapper<List<WeatherData>>>? {
        selectedDestinationPlace.value?.let { place ->
            getDataRangeForTrip(
                tripRangeStartDateValue.value!! + place.offsetUTC,
                tripRangeEndDateValue.value!! + place.offsetUTC
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

    /*
     Convert weather forecast to trip model
     */
    private fun convertWeatherListToWeatherCondition(weatherList: LiveData<ResourceWrapper<List<WeatherData>>>?) {
        if (weatherList != null) {
            selectedTripCondition.addSource(weatherList) {
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
                        selectedTripCondition.value = ResourceWrapper.error(it.errorCode!!, null)
                    }
                }
            }
        }
    }

    /*
     * If all conditions are checked and approved get weather forecast
     */
    private fun obtainSelectedDestinationWeatherRequest() {
        if (isConditionsValidBeforeSendingRequest()) {
            convertWeatherListToWeatherCondition(getSelectedPlaceWeatherData())
        }
    }

    /*
     * Check for proper destination and range conditions
     * Returns true when place and both dates properly selected
     * otherwise return false and apply error state with error code
     */
    private fun isConditionsValidBeforeSendingRequest(): Boolean {
        if ((tripRangeStartDateValue.value != null) && (tripRangeEndDateValue.value != null) && (tripRangeEndDateValue.value!! > 0)) {
            // return false and set error if place not selected
            when (selectedDestinationPlace.value) {
                null -> {
                    selectedTripCondition.value =
                        ResourceWrapper.error(ErrorCodes.EmptyDestinationException.code, null)
                    return false
                }
            }
            // compare trip dates
            when (isProperDataRangeSelected(
                tripRangeStartDateValue.value,
                tripRangeEndDateValue.value
            )) {
                // return false and set error if start date is greater than end date
                ERROR_START_DATE_GREATER -> {
                    selectedTripCondition.value =
                        ResourceWrapper.error(
                            ErrorCodes.StartDateIsGreaterException.code,
                            null
                        )
                    return false
                }
                // return false and set error if start or end date not initialized
                ERROR_DATE_FIELD_NULL -> {
                    selectedTripCondition.value =
                        ResourceWrapper.error(ErrorCodes.EmptyDatesException.code, null)
                    return false
                }
                // return false and set error if start date not selected
                ERROR_START_DATE_FIELD_ZERO -> {
                    selectedTripCondition.value =
                        ResourceWrapper.error(ErrorCodes.EmptyStartDateException.code, null)
                    return false
                }
                // return false and set error if selected date range greater than max granted
                ERROR_DATE_MAX_LENGTH_EXCEEDED -> {
                    selectedTripCondition.value =
                        ResourceWrapper.error(
                            ErrorCodes.TooLongDateRangeIntervalException.code,
                            null
                        )
                    return false
                }
            }
            // if all condition is matched
            return true
        }
        // if one of date not initialized or end date not selected
        return false
    }

    /*
    Clear trip condition values
     */
    fun clearTripSelection() {
        selectedDestinationPlace.value = null
        tripRangeStartDateValue.value = 0L
        tripRangeEndDateValue.value = 0L
    }

    /*
    Save selected place to local storage
     */
    fun saveLastSelectedPlaceToLocalStorage() {
        repository.setLastSelectedPlace(selectedDestinationPlace.value)
    }

    override fun onCleared() {
        super.onCleared()
        repository.unregisterCallback()
    }
}


