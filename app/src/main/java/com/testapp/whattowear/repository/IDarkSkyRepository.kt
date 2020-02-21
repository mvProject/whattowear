package com.testapp.whattowear.repository

import com.testapp.whattowear.data.WeatherData

interface IDarkSkyRepository {
    suspend fun getDarkSkyWeatherDataForDateRange() : List<WeatherData>
}