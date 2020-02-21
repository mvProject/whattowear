package com.testapp.whattowear.repository

import com.testapp.whattowear.BuildConfig
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.network.WeatherApiService
import com.testapp.whattowear.utils.convertToWeatherDataModel

class DarkSkyRepository : IDarkSkyRepository {

    private val api = WeatherApiService().initApi()

    override suspend fun getDarkSkyWeatherDataForDateRange(lat: String,lon: String,dataRange: List<Long>): List<WeatherData> {
        val weatherList = mutableListOf<WeatherData>()

        for (data in dataRange) {
            val current = api.getSingleForecastAsync(BuildConfig.DARKSKY_API_KEY, lat, lon, data.toString())
            weatherList.add(current.convertToWeatherDataModel())
        }
        return weatherList
    }
}