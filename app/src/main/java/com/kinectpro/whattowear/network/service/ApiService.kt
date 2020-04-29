package com.kinectpro.whattowear.network.service

import com.kinectpro.whattowear.BuildConfig
import com.kinectpro.whattowear.data.model.enums.ErrorCodes
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper
import com.kinectpro.whattowear.network.api.DarkSkyWeatherApiService
import com.kinectpro.whattowear.utils.convertCurrentLocaleLanguageToApiLanguageFormat
import com.kinectpro.whattowear.utils.convertToWeatherDataModel
import retrofit2.HttpException
import java.lang.NullPointerException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
            return ResourceWrapper.success(weatherList)
        } catch (e: Exception) {
            // show partially forecast if it exists
            if (weatherList.size > 0) {
                ResourceWrapper.success(weatherList)
            } else {
                ResourceWrapper.error(handleException(e), null)
            }
        }
    }

    private fun handleException(e: java.lang.Exception): Int {
        return when (e) {
            is HttpException -> e.code()
            is SocketTimeoutException -> ErrorCodes.SocketTimeOut.code
            is UnknownHostException -> ErrorCodes.UnknownHostException.code
            is NullPointerException -> ErrorCodes.NoForecastException.code
            else -> Int.MAX_VALUE
        }
    }
}
