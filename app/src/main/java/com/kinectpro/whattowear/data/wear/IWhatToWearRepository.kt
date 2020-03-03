package com.kinectpro.whattowear.data.wear

import com.kinectpro.whattowear.data.PlaceTrip
import com.kinectpro.whattowear.data.WeatherData
import com.kinectpro.whattowear.data.wear.model.WearItem
import com.kinectpro.whattowear.data.wear.model.WeatherTemp

interface IWhatToWearRepository {
    fun getWeatherForecastForSelectedPlace(place: PlaceTrip): List<WeatherData>
    fun getWearsAvailableForSelect(condition: List<WeatherTemp>): List<WearItem>
    fun getLastSaveSelectedPlace(): PlaceTrip
    fun getLastSaveSelectedDateRange(): List<Long>
    fun getLastSaveSelectedWears(): List<WearItem>
}