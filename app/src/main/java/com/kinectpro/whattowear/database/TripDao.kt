package com.kinectpro.whattowear.database

import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao
interface TripDao {
    @Query("SELECT * FROM trip_table")
    fun getAllTrips(): MutableLiveData<List<TripItem>>

    @Query("SELECT * FROM trip_table WHERE uid=:id")
    fun getTripItem(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trip: TripItem)

    @Update
    suspend fun update(trip: TripItem)

    @Delete
    suspend fun delete(trip: TripItem)

    @Query("DELETE FROM trip_table")
    suspend fun deleteAll()
}