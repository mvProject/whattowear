package com.kinectpro.whattowear.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wears")
data class WearEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "wear_name") val wearName: String,
    @ColumnInfo(name = "wear_checked") val wearChecked: Boolean,
    @ColumnInfo(name = "trip_id") val tripId: String?,
    @ColumnInfo(name = "is_default") val isDefault: Boolean
)