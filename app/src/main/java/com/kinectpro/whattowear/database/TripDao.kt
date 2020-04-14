package com.kinectpro.whattowear.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM trip_table")
    fun getAllTrips(): LiveData<List<TripItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: TripItem)

    @Update
    suspend fun update(trip: TripItem)

    @Delete
    suspend fun delete(trip: TripItem)

    @Query("DELETE FROM trip_table")
    suspend fun deleteAll()
}