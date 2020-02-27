package com.testapp.whattowear.data.item

import com.testapp.whattowear.data.item.model.TripItem
import com.testapp.whattowear.data.item.model.WeatherCond

class TripItemRepository : ITripItemRepository {
    override fun getItemsAvailableForSelect(condition: WeatherCond): List<TripItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}