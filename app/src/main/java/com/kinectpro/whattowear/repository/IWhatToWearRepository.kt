package com.kinectpro.whattowear.repository

import androidx.lifecycle.LiveData
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper

interface IWhatToWearRepository {
    fun getWeatherForecastForSelectedPlace(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<ResourceWrapper<List<WeatherData>>>?
}