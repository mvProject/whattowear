package com.testapp.whattowear.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.data.WeatherEvent

class DarkSkyWeatherRepository {

    private val darkSkyWeatherService = DarkSkyWeatherImpl()

    fun getDarkSkyWeatherLiveDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<WeatherEvent<List<WeatherData>>> = liveData {
        emit(WeatherEvent.loading())
        val data = darkSkyWeatherService.getDarkSkyWeatherDataForDateRange(lat, lon, dataRange)
        emit(WeatherEvent.success(data))
    }

}