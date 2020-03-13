package com.kinectpro.whattowear.network.service

import com.kinectpro.whattowear.BuildConfig
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.network.api.DarkSkyWeatherApiService
import com.kinectpro.whattowear.utils.convertToWeatherDataModel
import com.kinectpro.whattowear.utils.getProperLanguageValue

class ApiService : IDarkSkyWeather {

    private val api = DarkSkyWeatherApiService()
        .initApi()

    override suspend fun getDarkSkyWeatherDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): List<WeatherData> {
        val weatherList = mutableListOf<WeatherData>()

        for (data in dataRange) {
            val current =
                api.getSingleForecastAsync(
                    BuildConfig.DARKSKY_API_KEY, lat, lon, data.toString(),
                    getProperLanguageValue()
                )
            current.let {
                weatherList.add(current.convertToWeatherDataModel()!!)
            }
        }
        return weatherList
    }

}