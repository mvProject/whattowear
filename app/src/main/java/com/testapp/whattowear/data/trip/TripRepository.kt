package com.testapp.whattowear.data.trip

import com.testapp.whattowear.data.trip.ITripRepository
import com.testapp.whattowear.data.item.TripItemRepository
import com.testapp.whattowear.data.wear.TripWearRepository

// Facade

class TripRepository() : ITripRepository {

    val wearRepo = TripWearRepository()
    val itemRepo = TripItemRepository()

    // TODO get lists for trip and concat them

    override fun getTripNeededThings() {
        TODO("list of wears and items according selected place and date range") //To change body of created functions use File | Settings | File Templates.
    }
}