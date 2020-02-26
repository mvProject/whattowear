package com.testapp.whattowear.ui.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.utils.getDataRangeForTrip
import java.util.*
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.repository.DarkSkyWeatherRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val selectedDestinationPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    var tripStartDate = 0L
    var tripEndDate = 0L
    private val dataRange = mutableListOf<Long>().apply {
        add(1582114347)
        add(1582269744)
        add(1582356144)
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
            tripStartDate = calendar.timeInMillis
        }

    var tripEndDateSelectionDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            tripEndDate = calendar.timeInMillis
        }

    fun getDataRangeForTripListener() {
        Toast.makeText(
            getApplication(),
            getDataRangeForTrip(tripStartDate, tripEndDate).toString(),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun addNewCustomWear() {
        // TODO add new item feature
    }

    private val repository = DarkSkyWeatherRepository()

    fun getSelectedPlaceWeatherData(): LiveData<List<WeatherData>>? {
        selectedPlace.value?.let {
            return repository.getDarkSkyWeatherLiveDataForDateRange(
                it.latitude,
                it.longitude,
                dataRange
            )
        }
        return null
    }
