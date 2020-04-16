package com.kinectpro.whattowear.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.db.TripDatabase
import com.kinectpro.whattowear.utils.convertWearItemDbModelsToWearItemModels
import com.kinectpro.whattowear.utils.convertWearItemModelsToWearItemDbModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class WearRepository(context: Context, private val scope: CoroutineScope) : IWear {

    private val wearDao = TripDatabase.getInstance(context).wearDao

    override fun saveTripWearsToDatabase(wears: List<WearItem>) {
        scope.launch {
            wearDao.insertTripWears(wears.convertWearItemModelsToWearItemDbModels())
        }
    }

    override fun loadTripWearsFromDatabase(): LiveData<List<WearItem>> {
        return wearDao.getAllWears().map { it.convertWearItemDbModelsToWearItemModels() }
    }

    override fun deleteTripWearsToDatabase(wears: List<WearItem>) {
        scope.launch {
            wearDao.deleteTripWears(wears.convertWearItemModelsToWearItemDbModels())
        }
    }

}