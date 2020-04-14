package com.kinectpro.whattowear.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM trip_table")
    fun getAllTrips(): LiveData<List<TripItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trip: TripItem)

    @Update
    fun update(trip: TripItem)

    @Delete
    fun delete(trip: TripItem)

    @Query("DELETE FROM trip_table")
    fun deleteAll()
}