package com.testapp.whattowear.ui.viewmodel

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.dialog.TripDatePickerDialog
import com.testapp.whattowear.utils.getTripDataRange

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val selectedPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    var tripStartDate = 0L
    var tripEndDate = 0L

    fun getSelectedWeather(): PlaceSelectionListener {
        return object : PlaceSelectionListener {

            override fun onError(status: Status) {
                if (!status.statusMessage.isNullOrEmpty())
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



    fun startDateSelectListener(fm: FragmentManager) {
        val startDateFragment = TripDatePickerDialog(listener = tripDateTypeSelectedListener)
        startDateFragment.show(fm, TripDatePickerDialog.START_DATE_DIALOG)
    }

    fun endDateSelectListener(fm: FragmentManager) {
        val endBundle = Bundle()
        endBundle.putLong(TripDatePickerDialog.END_DATE_DIALOG, tripStartDate)
        val endDateFragment = TripDatePickerDialog(listener = tripDateTypeSelectedListener)
        endDateFragment.arguments = endBundle
        endDateFragment.show(fm, TripDatePickerDialog.END_DATE_DIALOG)
    }

    fun selectDataRangeListener() {
        Toast.makeText(
            getApplication(),
            getTripDataRange(tripStartDate, tripEndDate).toString(),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private var tripDateTypeSelectedListener =
        object : TripDatePickerDialog.DatePickerDialogListener {
            override fun getTripDateTypeSelectedValue(type: String, date: Long) {
                when (type) {
                    TripDatePickerDialog.START_DATE_DIALOG -> {
                        tripStartDate = date
                    }
                    TripDatePickerDialog.END_DATE_DIALOG -> {
                        tripEndDate = date
                    }
                }
            }
        }



    fun addNewCustomWear(){
        // TODO add new item feature
    }
}
