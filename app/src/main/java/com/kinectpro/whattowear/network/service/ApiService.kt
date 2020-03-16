package com.kinectpro.whattowear.network.service

import com.kinectpro.whattowear.BuildConfig
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper
import com.kinectpro.whattowear.data.wrapper.ResponseWrapper
import com.kinectpro.whattowear.network.api.DarkSkyWeatherApiService
import com.kinectpro.whattowear.utils.convertToWeatherDataModel
import com.kinectpro.whattowear.utils.convertCurrentLocaleLanguageToApiLanguageFormat

class ApiService : IDarkSkyWeather {

    private val api = DarkSkyWeatherApiService()
        .initApi()

    override suspend fun getDarkSkyWeatherDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): ResourceWrapper<List<WeatherData>> {
        val weatherList = mutableListOf<WeatherData>()

        return try {
            for (data in dataRange) {
                val current =
                    api.getSingleForecastAsync(
                        BuildConfig.DARKSKY_API_KEY, lat, lon, data.toString(),
                        convertCurrentLocaleLanguageToApiLanguageFormat()
                    )
                current.let {
                    weatherList.add(current.convertToWeatherDataModel()!!)
                }
            }
            return ResponseWrapper().handleSuccess(weatherList)
        } catch (e: Exception) {
            ResponseWrapper().handleException(e)
        }
    }
}