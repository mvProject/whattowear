package com.testapp.whattowear.data.item

import com.testapp.whattowear.data.item.model.TripItem
import com.testapp.whattowear.data.item.model.WeatherCond

class TripItemRepository : ITripItemRepository {
    override fun getItemsAvailableForSelect(condition: List<WeatherCond>): List<TripItem> {
        TODO("return list of items according weather conditions")
    }
}