package com.testapp.whattowear.repository

import androidx.lifecycle.MutableLiveData
import com.testapp.whattowear.data.WeatherData

interface IDarkSkyRepository {
    fun getWeatherDataForDateRange(lat: String, lon : String, dataRange : List<Long>) : MutableLiveData<List<WeatherData>>
}