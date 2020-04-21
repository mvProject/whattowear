package com.kinectpro.whattowear.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

@Suppress("UNCHECKED_CAST")
class TripInfoViewModelFactory(private val application: Application, private val id: String) :
    NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TripInfoViewModel(application, id) as T
    }
}