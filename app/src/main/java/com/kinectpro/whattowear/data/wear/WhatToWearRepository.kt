package com.kinectpro.whattowear.data.wear

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kinectpro.whattowear.data.PlaceTrip
import com.kinectpro.whattowear.data.ResourceWrapper
import com.kinectpro.whattowear.data.WeatherData
import com.kinectpro.whattowear.data.wear.model.WearItem
import com.kinectpro.whattowear.data.wear.model.WeatherTemp
import com.kinectpro.whattowear.repository.ApiService

class WhatToWearRepository :
    IWhatToWearRepository {

    private val apiManager = ApiService()

    override fun getWeatherForecastForSelectedPlace(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<ResourceWrapper<List<WeatherData>>>  = liveData {
        emit(ResourceWrapper.loading())
        val data = apiManager.getDarkSkyWeatherDataForDateRange(lat, lon, dataRange)
        emit(ResourceWrapper.success(data))
    }


    override fun getWearsAvailableForSelect(condition: List<WeatherTemp>): List<WearItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastSaveSelectedPlace(): PlaceTrip {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastSaveSelectedDateRange(): List<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastSaveSelectedWears(): List<WearItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}