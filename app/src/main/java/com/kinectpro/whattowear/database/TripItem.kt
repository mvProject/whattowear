package com.kinectpro.whattowear.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_table")
data class TripItem(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "destination_id") val destinationId: String,
    @ColumnInfo(name = "destination_place") val destinationPlace: String,
    @ColumnInfo(name = "start_date") val startDate: Long,
    @ColumnInfo(name = "end_date") val endDate: Long
)