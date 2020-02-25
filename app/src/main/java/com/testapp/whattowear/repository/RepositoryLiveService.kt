package com.testapp.whattowear.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testapp.whattowear.BuildConfig
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.network.WeatherApiService
import com.testapp.whattowear.utils.convertToWeatherDataModel

class RepositoryLiveService() : IRepositoryLive{
    private val api = WeatherApiService().initApi()

    override suspend fun getDarkSkyWeatherDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): List<WeatherData> {
        val weatherList = mutableListOf<WeatherData>()

        for (data in dataRange) {
            val current = api.getSingleForecastAsync(BuildConfig.DARKSKY_API_KEY, lat, lon, data.toString())
            weatherList.add(current.convertToWeatherDataModel())
        }
        Log.d("Wear", "RepositoryLiveService - weatherList - $weatherList")
        return weatherList
    }

}