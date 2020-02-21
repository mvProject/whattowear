package com.testapp.whattowear.repository

import com.testapp.whattowear.data.WeatherData
import kotlinx.coroutines.*

class DarkSkyRepository : IDarkSkyRepository{
    private val darkSkyService = DarkSkyService()
    private var myJob: Job? = null
    private var weatherDataList = mutableListOf<WeatherData>()

    override fun getWeatherDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): List<WeatherData> {
        myJob = CoroutineScope(Dispatchers.IO).launch {
            val weatherList = darkSkyService.getDarkSkyWeatherDataForDateRange(lon,lon,dataRange)
            withContext(Dispatchers.Main) {
                weatherDataList.addAll(weatherList)
            }
        }
        return weatherDataList
    }
}