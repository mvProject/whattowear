package com.kinectpro.whattowear.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kinectpro.whattowear.database.dao.TripDao
import com.kinectpro.whattowear.database.dao.WearDao
import com.kinectpro.whattowear.database.entity.TripEntity
import com.kinectpro.whattowear.database.entity.WearEntity

@Database(
    entities = [TripEntity::class, WearEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TripDatabase : RoomDatabase() {

    abstract val tripDao: TripDao
    abstract val wearDao: WearDao

    companion object {

        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getInstance(context: Context): TripDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TripDatabase::class.java,
                        "trip_database"
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}