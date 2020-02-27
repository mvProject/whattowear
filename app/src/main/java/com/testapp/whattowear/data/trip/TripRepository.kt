package com.testapp.whattowear.data.trip

import com.testapp.whattowear.data.item.TripItemRepository
import com.testapp.whattowear.data.wear.TripWearRepository

// Facade

class TripRepository() : ITripRepository {

    val wearRepo = TripWearRepository()
    val itemRepo = TripItemRepository()


    // TODO get lists of wear and items for trip and concat them to result list

    override fun getTripNeededThings() {
        TODO("return result list what to wear")
    }
}