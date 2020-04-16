package com.kinectpro.whattowear.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripDatabaseModel(
    @PrimaryKey @ColumnInfo(name = "trip_id") val tripId: String,
    @ColumnInfo(name = "destination_id") val destinationId: String,
    @ColumnInfo(name = "destination_place") val destinationPlace: String,
    @ColumnInfo(name = "night_temp") val nightTemp: String,
    @ColumnInfo(name = "day_temp") val dayTemp: String,
    @ColumnInfo(name = "start_date") val startDate: Long,
    @ColumnInfo(name = "end_date") val endDate: Long
)