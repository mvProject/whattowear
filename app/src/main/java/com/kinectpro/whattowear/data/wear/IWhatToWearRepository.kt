package com.testapp.whattowear.data.wear

import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.WeatherData
import com.testapp.whattowear.data.wear.model.WearItem
import com.testapp.whattowear.data.wear.model.WeatherTemp

interface IWhatToWearRepository {
    fun getWeatherForecastForSelectedPlace(place: PlaceTrip): List<WeatherData>
    fun getWearsAvailableForSelect(condition: List<WeatherTemp>): List<WearItem>
    fun getLastSaveSelectedPlace(): PlaceTrip
    fun getLastSaveSelectedDateRange(): List<Long>
    fun getLastSaveSelectedWears(): List<WearItem>
}