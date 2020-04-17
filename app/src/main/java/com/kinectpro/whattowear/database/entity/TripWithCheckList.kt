package com.kinectpro.whattowear.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TripWithCheckList(
    @Embedded val trip: TripEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "trip_id"
    )
    val checkList: List<WearEntity>
)