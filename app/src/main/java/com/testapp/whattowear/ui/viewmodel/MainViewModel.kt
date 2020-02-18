package com.testapp.whattowear.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testapp.whattowear.data.PlaceTrip

class MainViewModel : ViewModel() {

    val selectedPlace = MutableLiveData<PlaceTrip>()
}
