package com.kinectpro.whattowear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kinectpro.whattowear.database.entity.WearEntity

@Dao
interface WearDao {
    @Query("SELECT * FROM wears")
    fun getAllWears(): LiveData<List<WearEntity>>

    @Query("SELECT * FROM wears WHERE trip_id = :tripId")
    fun getTripWears(tripId: String): LiveData<List<WearEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTripWears(wears: List<WearEntity>)

    @Delete
    suspend fun deleteTripWears(wears: List<WearEntity>)
}