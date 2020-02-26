package com.testapp.whattowear.repository

import com.testapp.whattowear.BuildConfig
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.network.DarkSkyWeatherApiService
import com.testapp.whattowear.utils.convertToWeatherDataModel

class DarkSkyWeatherImpl() : IDarkSkyWeather {

    private val api = DarkSkyWeatherApiService().initApi()

    override suspend fun getDarkSkyWeatherDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): List<WeatherData> {
        val weatherList = mutableListOf<WeatherData>()

        for (data in dataRange) {
            val current =
                api.getSingleForecastAsync(BuildConfig.DARKSKY_API_KEY, lat, lon, data.toString())
            current.let {
                weatherList.add(current.convertToWeatherDataModel()!!)
            }

        }
        return weatherList
    }

}