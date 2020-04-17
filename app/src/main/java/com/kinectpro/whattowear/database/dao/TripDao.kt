package com.kinectpro.whattowear.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kinectpro.whattowear.database.entity.TripEntity
import com.kinectpro.whattowear.database.entity.TripWithCheckList

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    fun getAllTrips(): LiveData<List<TripEntity>>

    @Transaction
    @Query("SELECT * FROM trips")
    fun getAllTripsWithCheckLists(): LiveData<List<TripWithCheckList>>

    @Transaction
    @Query("SELECT * FROM trips WHERE id = :id")
    fun getSingleTripWithCheckLists(id: String): LiveData<TripWithCheckList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: TripEntity)

    @Update
    suspend fun update(trip: TripEntity)

    @Delete
    suspend fun delete(trip: TripEntity)

    @Query("DELETE FROM trips")
    suspend fun deleteAll()
}