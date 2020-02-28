package com.testapp.whattowear.ui.viewmodel

import android.app.Application
import android.app.DatePickerDialog
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

    private val repository = DarkSkyWeatherRepository()
    val selectedDestinationPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()
    var tripStartDate = 0L
    var tripEndDate = 0L

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

    fun getSelectedPlaceWeatherData(): LiveData<List<WeatherData>>? {
        selectedDestinationPlace.value?.let { place ->
            getDataRangeForTrip(tripStartDate, tripEndDate)?.let {
                return repository.getDarkSkyWeatherLiveDataForDateRange(
                    place.latitude,
                    place.longitude,
                    it
                )
            }
        }
        return null
    }

    fun addNewCustomWear() {
        // TODO add new item feature
    }
}
