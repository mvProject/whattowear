package com.kinectpro.whattowear.database

import com.kinectpro.whattowear.data.model.wear.WearItem

interface IWear {
    fun saveTripWearsToDatabase(wears: List<WearItem>)
    fun loadTripWearsFromDatabase(): List<WearItem>?
    fun deleteTripWearsToDatabase(wears: List<WearItem>)
}