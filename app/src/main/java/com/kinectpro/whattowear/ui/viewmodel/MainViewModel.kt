package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.kinectpro.whattowear.data.IWeatherRangeSummary
import com.kinectpro.whattowear.data.TripWeatherCondition
import com.kinectpro.whattowear.data.model.enums.ErrorCodes
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.data.storage.WhatToWearCache
import com.kinectpro.whattowear.utils.getDataRangeForTrip
import com.kinectpro.whattowear.data.model.enums.ResourceStatus as RequestStatus
import java.util.*
import com.kinectpro.whattowear.repository.WhatToWearRepository
import com.kinectpro.whattowear.utils.*
import java.util.concurrent.TimeUnit

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

    var tripStartDateSelectionDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            tripRangeStartDateValue.value = calendar.timeInMillis
            if ((tripRangeStartDateValue.value != null) && (tripRangeEndDateValue.value != null)) {
                if (tripRangeStartDateValue.value!! > tripRangeEndDateValue.value!!) {
                    tripRangeEndDateValue.value = tripRangeStartDateValue.value
                }
            }
        }

    var tripEndDateSelectionDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            tripRangeEndDateValue.value = calendar.timeInMillis
        }
/*
    fun addNewCustomWear() {
        // TODO commented in UncompleteUI feature,uncomment when functionality will be performed
    }
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

    /**
     * Check for proper destination and range conditions and get weather forecast, otherwise send proper error message
     */
    private fun obtainSelectedDestinationWeatherRequest() {
        if ((tripRangeStartDateValue.value != null) && (tripRangeEndDateValue.value != null)) {
            if ((tripRangeStartDateValue.value!! > 0) && (tripRangeEndDateValue.value!! > 0)) {
                if (isConditionsValidBeforeSendingRequest()) {
                    convertWeatherListToWeatherCondition(getSelectedPlaceWeatherData())
                }
            }
        }
    }

    /**
     *
     */
    private fun isConditionsValidBeforeSendingRequest(): Boolean {
        if (selectedDestinationPlace.value == null) {
            selectedTripCondition.value =
                ResourceWrapper.error(ErrorCodes.EmptyDestinationException.code, null)
            return false
        }
        when (isProperDataRangeSelected(
            tripRangeStartDateValue.value,
            tripRangeEndDateValue.value
        )) {
            DATE_ERROR_FIELD_EMPTY_OR_ZERO_LESS -> {
                selectedTripCondition.value =
                    ResourceWrapper.error(ErrorCodes.EmptyDatesException.code, null)
                return false
            }
            DATE_ERROR_MAX_LENGTH_EXCEEDED -> {
                selectedTripCondition.value =
                    ResourceWrapper.error(
                        ErrorCodes.TooLongDateRangeIntervalException.code,
                        null
                    )
                return false
            }
        }
        return true
    }

    fun saveLastSelectedPlaceToLocalStorage() {
        repository.setLastSelectedPlace(selectedDestinationPlace.value)
    }

    override fun onCleared() {
        super.onCleared()
        repository.unregisterCallback()
    }
}


