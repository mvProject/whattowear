package com.testapp.whattowear.ui.viewmodel

import android.app.Application
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.dialog.TripDatePickerDialog
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val selectedPlace = MutableLiveData<PlaceTrip>()
    val selectedPlaceStatus = MutableLiveData<String>()

    var tripStartDate = 0L
    var tripEndDate = 0L

    fun getSelectedWeather(): PlaceSelectionListener {
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

    // TODO move to utils later
    private fun getTripDataRange(startDate: Long, endDate: Long): List<Long>? {
        if ((startDate > 0) and (endDate > 0) and (endDate > startDate)) {
            val dataRange = mutableListOf<Long>()
            for (item in startDate..endDate step DateUtils.DAY_IN_MILLIS)
                dataRange.add(TimeUnit.MILLISECONDS.toSeconds(item))
            return dataRange
        }
        return null
    }

    fun startDateSelectListener(fm : FragmentManager) {
        val startDateFragment = TripDatePickerDialog()
        startDateFragment.show(fm, TripDatePickerDialog.START_DATE_DIALOG)
    }

    fun endDateSelectListener(fm : FragmentManager){
        val endBundle = Bundle()
        val endDateFragment = TripDatePickerDialog()
        endDateFragment.arguments = endBundle
        endBundle.putLong(TripDatePickerDialog.END_DATE_DIALOG, tripStartDate)
        endDateFragment.show(fm, TripDatePickerDialog.END_DATE_DIALOG)
    }

    fun selectDataRangeListener(){
        Toast.makeText(
            getApplication(),getTripDataRange(tripStartDate,tripEndDate).toString(),Toast.LENGTH_SHORT)
            .show()
    }



}
