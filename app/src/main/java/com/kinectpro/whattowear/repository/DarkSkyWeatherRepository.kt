package com.kinectpro.whattowear.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kinectpro.whattowear.data.wear.WeatherData
import com.kinectpro.whattowear.data.wear.ResourceWrapper

class DarkSkyWeatherRepository : IDarkSkyWeatherRepository {

    private val darkSkyWeatherService =
        DarkSkyWeatherImpl()

    override fun getDarkSkyWeatherLiveDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<ResourceWrapper<List<WeatherData>>> = liveData {
        emit(ResourceWrapper.loading())
        val data = darkSkyWeatherService.getDarkSkyWeatherDataForDateRange(lat, lon, dataRange)
        emit(ResourceWrapper.success(data))
    }

}