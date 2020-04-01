package com.kinectpro.whattowear.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kinectpro.whattowear.data.model.enums.ErrorCodes
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.storage.ICache
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper
import com.kinectpro.whattowear.network.service.ApiService
import com.kinectpro.whattowear.utils.NetworkChecker

class WhatToWearRepository(
    private val networkChecker: NetworkChecker,
    private val localStorage: ICache
) :
    IWhatToWearRepository {

    private val apiManager =
        ApiService()

    override fun getWeatherForecastForSelectedPlace(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<ResourceWrapper<List<WeatherData>>> = liveData {
        if (networkChecker.isInternetConnected()) {
            emit(ResourceWrapper.loading())
            emit(apiManager.getDarkSkyWeatherDataForDateRange(lat, lon, dataRange)).apply {
            }
        } else {
            emit(ResourceWrapper.error(ErrorCodes.NoInternetConnectionException.code, null))
        }
    }

    fun unregisterCallback() {
        networkChecker.unregisterNetworkCallback()
    }

    fun getLastSelectedPlace(): PlaceTrip? {
        return localStorage.getLastSelectedPlace()
    }

    fun setLastSelectedPlace(selectedPlace: PlaceTrip?) {
        localStorage.setLastSelectedPlace(selectedPlace)
    }
}