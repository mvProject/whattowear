package com.testapp.whattowear.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.data.ResourceWrapper

class DarkSkyWeatherRepository {

    private val darkSkyWeatherService = DarkSkyWeatherImpl()

    fun getDarkSkyWeatherLiveDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<ResourceWrapper<List<WeatherData>>> = liveData {
        emit(ResourceWrapper.loading())
        val data = darkSkyWeatherService.getDarkSkyWeatherDataForDateRange(lat, lon, dataRange)
        emit(ResourceWrapper.success(data))
    }

}