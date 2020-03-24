package com.kinectpro.whattowear.network.service

import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper

/**
 * Interface contain method for getting weather list which must be launching async
 * @param lat latitude of selected place
 * @param lon longitude of selected place
 * @param dataRange list of selected timestamps
 * @return list of weather data items
 * */
interface IDarkSkyWeather {
    suspend fun getDarkSkyWeatherDataForDateRange(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): ResourceWrapper<List<WeatherData>>
}