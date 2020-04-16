package com.kinectpro.whattowear.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.db.TripDatabase
import com.kinectpro.whattowear.database.entity.TripWithCheckList
import com.kinectpro.whattowear.utils.convertTripDbModelsToTripModels
import com.kinectpro.whattowear.utils.convertTripModelToTripDbModel
import com.kinectpro.whattowear.utils.convertWearItemModelsToWearItemDbModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TripRepository(context: Context, private val scope: CoroutineScope) :
    ITrip {

    private val tripDao = TripDatabase.getInstance(context).tripDao
    private val wearDao = TripDatabase.getInstance(context).wearDao

    override fun saveTripToDatabase(trip: TripItem) {
        scope.launch {
            tripDao.insert(trip.convertTripModelToTripDbModel())
        }
    }

    override fun saveTripToDatabase(trip: TripItem, checkList: List<WearItem>) {
        scope.launch {
            tripDao.insert(trip.convertTripModelToTripDbModel())
            wearDao.insertTripWears(checkList.convertWearItemModelsToWearItemDbModels())
        }
    }

    override fun loadAllTripsFromDatabase(): LiveData<List<TripItem>> {
        return tripDao.getAllTrips().map { it.convertTripDbModelsToTripModels() }
    }

    override fun loadAllTripsWithCheckListsFromDatabase(): LiveData<List<TripWithCheckList>> {
        return tripDao.getAllTripsWithCheckLists()
    }

    override fun loadSingleTripWithCheckListsFromDatabase(tripId: String): LiveData<TripWithCheckList> {
        return tripDao.getSingleTripWithCheckLists(tripId)
    }

    override fun updateSelectedTrip(trip: TripItem) {
        scope.launch {
            tripDao.update(trip.convertTripModelToTripDbModel())
        }
    }

    override fun deleteSelectedTrip(trip: TripItem) {
        val wears = wearDao.getTripWears(trip.tripId).value
        scope.launch {
            tripDao.delete(trip.convertTripModelToTripDbModel())
            if (wears != null) {
                wearDao.deleteTripWears(wears)
            }
        }
    }

    override fun clearDatabase() {
        scope.launch {
            tripDao.deleteAll()
        }
    }

}