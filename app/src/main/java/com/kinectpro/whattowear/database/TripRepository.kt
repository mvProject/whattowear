package com.kinectpro.whattowear.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.db.TripDatabase
import com.kinectpro.whattowear.database.entity.TripWithCheckList
import com.kinectpro.whattowear.utils.convertTripEntitiesToTripModels
import com.kinectpro.whattowear.utils.convertTripModelToTripEntity
import com.kinectpro.whattowear.utils.convertWearItemsToWearEntities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TripRepository(context: Context, private val scope: CoroutineScope) :
    ITrip {

    private val tripDao = TripDatabase.getInstance(context).tripDao
    private val wearDao = TripDatabase.getInstance(context).wearDao

    private fun createCheckList(trip: TripItem): List<WearItem> {
        return listOf(
            WearItem("Documents", true, trip.id),
            WearItem("Money", false, trip.id),
            WearItem("First aid kit", false, trip.id)
        )
    }

    override fun saveTripToDatabase(trip: TripItem, isDefaultListChecked: Boolean) {
        scope.launch {
            tripDao.insert(trip.convertTripModelToTripEntity())
            if (isDefaultListChecked) {
                wearDao.insertTripWears(createCheckList(trip).convertWearItemsToWearEntities())
            }
        }
    }

    override fun loadAllTripsFromDatabase(): LiveData<List<TripItem>> {
        return tripDao.getAllTrips().map { it.convertTripEntitiesToTripModels() }
    }

    override fun loadAllTripsWithCheckListsFromDatabase(): LiveData<List<TripWithCheckList>> {
        return tripDao.getAllTripsWithCheckLists()
    }

    override fun loadSingleTripWithCheckListsFromDatabase(tripId: String): LiveData<TripWithCheckList> {
        return tripDao.getSingleTripWithCheckLists(tripId)
    }

    override fun updateSelectedTrip(trip: TripItem) {
        scope.launch {
            tripDao.update(trip.convertTripModelToTripEntity())
        }
    }

    override fun deleteSelectedTrip(trip: TripItem) {
        val wears = wearDao.getTripWears(trip.id).value
        scope.launch {
            tripDao.delete(trip.convertTripModelToTripEntity())
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