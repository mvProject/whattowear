package com.kinectpro.whattowear.repository

import androidx.lifecycle.LiveData
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper
import com.kinectpro.whattowear.data.response.WeatherData

interface IDarkSkyWeatherRepository {
    fun getDarkSkyWeatherLiveDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<ResourceWrapper<List<WeatherData>>>
}