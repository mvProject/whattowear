package com.kinectpro.whattowear.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TripDatabaseModel::class], version = 1, exportSchema = false)
abstract class TripDatabase : RoomDatabase() {

    abstract val tripDatabaseDao: TripDao

    companion object {

        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getInstance(context: Context): TripDatabase {
            synchronized(this) {
                var instance = INSTANCE

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