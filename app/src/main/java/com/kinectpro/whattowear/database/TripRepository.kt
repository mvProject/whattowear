package com.kinectpro.whattowear.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kinectpro.whattowear.R
import com.kinectpro.whattowear.data.model.trip.TripItem
import com.kinectpro.whattowear.data.model.wear.ITEM_TYPE_DEFAULT
import com.kinectpro.whattowear.data.model.wear.WearItem
import com.kinectpro.whattowear.database.db.TripDatabase
import com.kinectpro.whattowear.database.entity.TripWithCheckList
import com.kinectpro.whattowear.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TripRepository(context: Context, private val scope: CoroutineScope) :
    ITrip {

    private val tripDao = TripDatabase.getInstance(context).tripDao
    private val wearDao = TripDatabase.getInstance(context).wearDao
    private val defaultList = context.resources.getStringArray(R.array.default_check_list).toList()

    override fun saveTripToDatabase(
        trip: TripItem,
        isDefaultListChecked: Boolean
    ) {
        scope.launch {
            tripDao.insert(trip.convertTripModelToTripEntity())
            if (isDefaultListChecked) {
                wearDao.insertTripWears(
                    defaultList.getWearsWithIds(trip.id, ITEM_TYPE_DEFAULT)
                        .convertWearItemsToWearEntities()
                )
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

    override fun updateWears(wears: List<WearItem>) {
        scope.launch {
            wearDao.updateTripWears(wears.convertWearItemsToWearEntities())
        }
    }

    override fun updateSelectedWear(wear: WearItem) {
        scope.launch {
            wearDao.updateWear(wear.convertWearItemToWearEntity())
        }
    }

    override fun deleteSelectedWear(wear: WearItem) {
        scope.launch {
            wearDao.deleteWear(wear.convertWearItemToWearEntity())
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

    override fun saveWearToDatabase(wear: WearItem) {
        scope.launch {
            wearDao.insertWear(wear.convertWearItemToWearEntity())
        }
    }

    override fun clearDatabase() {
        scope.launch {
            tripDao.deleteAll()
        }
    }
}