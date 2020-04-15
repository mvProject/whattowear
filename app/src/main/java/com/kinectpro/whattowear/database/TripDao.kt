package com.kinectpro.whattowear.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    fun getAllTrips(): LiveData<List<TripDatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: TripDatabaseModel)

    @Update
    suspend fun update(trip: TripDatabaseModel)

    @Delete
    suspend fun delete(trip: TripDatabaseModel)

    @Query("DELETE FROM trips")
    suspend fun deleteAll()
}