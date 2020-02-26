package com.testapp.whattowear.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.testapp.whattowear.data.WeatherData

class DarkSkyWeatherRepository {

    private val darkSkyWeatherService = DarkSkyWeatherImpl()

    fun getDarkSkyWeatherLiveDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<List<WeatherData>> = liveData {
        val data = darkSkyWeatherService.getDarkSkyWeatherDataForDateRange(lat, lon, dataRange)
        emit(data)
    }

}