package com.kinectpro.whattowear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kinectpro.whattowear.database.entity.TripDatabaseModel
import com.kinectpro.whattowear.database.entity.TripWithCheckList

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    fun getAllTrips(): LiveData<List<TripDatabaseModel>>

    @Transaction
    @Query("SELECT * FROM trips")
    fun getAllTripsWithCheckLists(): LiveData<List<TripWithCheckList>>

    @Transaction
    @Query("SELECT * FROM trips WHERE trip_id = :tripId")
    fun getSingleTripWithCheckLists(tripId: String): LiveData<TripWithCheckList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: TripDatabaseModel)

    @Update
    suspend fun update(trip: TripDatabaseModel)

    @Delete
    suspend fun delete(trip: TripDatabaseModel)

    @Query("DELETE FROM trips")
    suspend fun deleteAll()
}