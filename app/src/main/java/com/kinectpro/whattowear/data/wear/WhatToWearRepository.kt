package com.kinectpro.whattowear.data.wear

import com.kinectpro.whattowear.data.PlaceTrip
import com.kinectpro.whattowear.data.WeatherData
import com.kinectpro.whattowear.data.wear.model.WearItem
import com.kinectpro.whattowear.data.wear.model.WeatherTemp

class WhatToWearRepository :
    IWhatToWearRepository {
    override fun getWeatherForecastForSelectedPlace(place: PlaceTrip): List<WeatherData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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