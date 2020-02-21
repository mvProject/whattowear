package com.testapp.whattowear.repository

import com.testapp.whattowear.data.WeatherData

interface IDarkSkyRepository {
    suspend fun getDarkSkyWeatherDataForDateRange(lat: String, lon : String, dataRange : List<Long>) : List<WeatherData>
}