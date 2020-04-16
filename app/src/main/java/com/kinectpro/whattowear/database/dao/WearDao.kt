package com.kinectpro.whattowear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kinectpro.whattowear.database.entity.WearDatabaseModel

@Dao
interface WearDao {
    @Query("SELECT * FROM wears")
    fun getAllWears(): LiveData<List<WearDatabaseModel>>

    @Query("SELECT * FROM wears WHERE wear_trip_id = :tripId")
    fun getTripWears(tripId: String): LiveData<List<WearDatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleWear(wear: WearDatabaseModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTripWears(wears: List<WearDatabaseModel>)

    @Update
    suspend fun update(wear: WearDatabaseModel)

    @Delete
    suspend fun deleteTripWears(wears: List<WearDatabaseModel>)

    @Query("DELETE FROM wears")
    suspend fun deleteAll()
}