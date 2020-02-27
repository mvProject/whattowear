package com.testapp.whattowear.data.wear

import com.testapp.whattowear.data.wear.model.WearItem
import com.testapp.whattowear.data.wear.model.WeatherTemp

class TripWearRepository : ITripWearRepository {
    override fun getWearsAvailableForSelect(temp: List<WeatherTemp>): List<WearItem> {
        TODO("return list of wears according each temperature")
    }
}