package com.kinectpro.whattowear.repository

import com.kinectpro.whattowear.data.location.PlaceTrip
import com.kinectpro.whattowear.data.response.WeatherData
import com.kinectpro.whattowear.data.wear.model.item.WearItem
import com.kinectpro.whattowear.data.wear.model.enums.WeatherTemp

interface IWhatToWearRepository {
    fun getWeatherForecastForSelectedPlace(place: PlaceTrip): List<WeatherData>
    fun getWearsAvailableForSelect(condition: List<WeatherTemp>): List<WearItem>
    fun getLastSaveSelectedPlace(): PlaceTrip
    fun getLastSaveSelectedDateRange(): List<Long>
    fun getLastSaveSelectedWears(): List<WearItem>
}