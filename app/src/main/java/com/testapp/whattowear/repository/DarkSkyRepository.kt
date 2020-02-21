package com.testapp.whattowear.repository

import com.testapp.whattowear.data.WeatherData
import kotlinx.coroutines.*
import androidx.lifecycle.MutableLiveData



class DarkSkyRepository : IDarkSkyRepository{
    private val darkSkyService = DarkSkyService()
    private var myJob: Job? = null

    override fun getWeatherDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): MutableLiveData<List<WeatherData>> {
        val weatherData = MutableLiveData<List<WeatherData>>()
        myJob = CoroutineScope(Dispatchers.IO).launch {
            val weatherList = darkSkyService.getDarkSkyWeatherDataForDateRange(lon,lon,dataRange)
            withContext(Dispatchers.Main) {
                weatherData.value = weatherList
            }
        }
        return weatherData
    }
}