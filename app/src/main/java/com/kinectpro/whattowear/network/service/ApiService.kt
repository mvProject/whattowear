package com.kinectpro.whattowear.network.service

import com.kinectpro.whattowear.BuildConfig
import com.kinectpro.whattowear.data.model.enums.ErrorCodes
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper
import com.kinectpro.whattowear.network.api.DarkSkyWeatherApiService
import com.kinectpro.whattowear.utils.convertToWeatherDataModel
import com.kinectpro.whattowear.utils.convertCurrentLocaleLanguageToApiLanguageFormat
import retrofit2.HttpException
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
            return handleSuccess(weatherList)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun <T : Any> handleSuccess(data: T): ResourceWrapper<T> {
        return ResourceWrapper.success(data)
    }

    private fun <T : Any> handleException(e: java.lang.Exception): ResourceWrapper<T> {
        return when (e) {
            is HttpException -> ResourceWrapper.error(e.code(), null)
            is SocketTimeoutException -> ResourceWrapper.error(ErrorCodes.SocketTimeOut.code, null)
            is UnknownHostException -> ResourceWrapper.error(
                ErrorCodes.UnknownHostException.code,
                null
            )
            else -> ResourceWrapper.error(Int.MAX_VALUE, null)
        }
    }
}
