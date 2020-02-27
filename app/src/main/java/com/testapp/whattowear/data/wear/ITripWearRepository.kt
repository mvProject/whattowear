package com.testapp.whattowear.data.wear

import com.testapp.whattowear.data.wear.model.WearItem
import com.testapp.whattowear.data.wear.model.WeatherTemp

interface ITripWearRepository {
    fun getWearsAvailableForSelect(temp: WeatherTemp): List<WearItem>
}