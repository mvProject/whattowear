package com.testapp.whattowear.network

import com.testapp.whattowear.BuildConfig
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.utils.convertToWeatherDataModel

class WeatherRepository {




    private val api = WeatherApiService().initApi()

    suspend fun getWeatherDataForDateRange(lat: String, lon : String, dataRange : List<Long>) : MutableList<WeatherData>{
        val weatherList = mutableListOf<WeatherData>()
        for (data in dataRange){
            val current = api.getSingleForecastAsync(BuildConfig.DARKSKY_API_KEY,lat,lon,data.toString()).await()
            weatherList.add(current.convertToWeatherDataModel())
        }
        return weatherList
    }
}