package com.testapp.whattowear.data.wear

import com.testapp.whattowear.data.PlaceTrip
import com.testapp.whattowear.data.wear.model.WearItem
import com.testapp.whattowear.data.wear.model.WeatherTemp

class WhatToWearRepository :
    IWhatToWearRepository {
    override fun getWearsAvailableForSelect(condition: List<WeatherTemp>): List<WearItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastSaveSelectedPlace(): PlaceTrip {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastSaveSelectedDateRange(): List<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastSaveSelectedWears(): List<WearItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}