package com.testapp.whattowear.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.testapp.whattowear.data.WeatherData


class RepositoryLive() {
    private val repositoryLiveService = RepositoryLiveService()

    fun getDarkSkyWeatherLiveDataForDateRange(lat: String, lon: String, dataRange: List<Long>): LiveData<List<WeatherData>> = liveData{
        val data = repositoryLiveService.getDarkSkyWeatherDataForDateRange(lat,lon,dataRange)
        Log.d("Wear", "RepositoryLive - data - $data")
        emit(data)
    }

}