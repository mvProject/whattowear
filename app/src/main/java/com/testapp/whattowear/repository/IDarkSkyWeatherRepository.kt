package com.testapp.whattowear.repository

import androidx.lifecycle.LiveData
import com.testapp.whattowear.data.ResourceWrapper
import com.testapp.whattowear.data.WeatherData

interface IDarkSkyWeatherRepository {
    fun getDarkSkyWeatherLiveDataForDateRange(
            lat: String,
            lon: String,
            dataRange: List<Long>
    ): LiveData<ResourceWrapper<List<WeatherData>>>
}