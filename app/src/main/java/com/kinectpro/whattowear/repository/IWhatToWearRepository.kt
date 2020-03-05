package com.kinectpro.whattowear.repository

import androidx.lifecycle.LiveData
import com.kinectpro.whattowear.data.model.location.PlaceTrip
import com.kinectpro.whattowear.data.model.response.WeatherData
import com.kinectpro.whattowear.data.model.trip.TripModel
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.data.model.wear.WeatherTemp
import com.kinectpro.whattowear.data.wrapper.ResourceWrapper

interface IWhatToWearRepository {
    fun getWeatherForecastForSelectedPlace(
        lat: String,
        lon: String,
        dataRange: List<Long>
    ): LiveData<ResourceWrapper<TripModel>>?

    fun getWearsAvailableForSelect(condition: List<WeatherTemp>): List<WearItem>
    fun getLastSaveSelectedPlace(): PlaceTrip
    fun getLastSaveSelectedDateRange(): List<Long>
    fun getLastSaveSelectedWears(): List<WearItem>
}