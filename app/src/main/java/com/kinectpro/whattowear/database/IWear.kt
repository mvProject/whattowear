package com.kinectpro.whattowear.database

import androidx.lifecycle.LiveData
import com.kinectpro.whattowear.data.model.wear.WearItem

interface IWear {
    fun saveTripWearsToDatabase(wears: List<WearItem>)
    fun loadTripWearsFromDatabase(): LiveData<List<WearItem>>
    fun deleteTripWearsToDatabase(wears: List<WearItem>)
}