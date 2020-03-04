package com.kinectpro.whattowear.repository

import androidx.lifecycle.LiveData
import com.kinectpro.whattowear.data.ResourceWrapper
import com.kinectpro.whattowear.data.WeatherData

interface IDarkSkyWeatherRepository {
    fun getDarkSkyWeatherLiveDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<ResourceWrapper<List<WeatherData>>>
}