package com.testapp.whattowear.data.item

import com.testapp.whattowear.data.item.model.TripItem
import com.testapp.whattowear.data.item.model.WeatherCond

interface ITripItemRepository {
    fun getItemsAvailableForSelect(condition: WeatherCond): List<TripItem>
}