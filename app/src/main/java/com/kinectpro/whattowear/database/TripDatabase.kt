package com.kinectpro.whattowear.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TripItem::class], version = 1, exportSchema = false)
abstract class TripDatabase : RoomDatabase() {

    abstract val tripDatabaseDao: TripDao

    companion object {

        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): TripDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TripDatabase::class.java,
                        "trip_database"
                    )
                        .addCallback(TripDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        private class TripDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        database.tripDatabaseDao.deleteAll()
                        val trip = TripItem(1, "11", "London", 1583704800000L, 1583791200000L)
                        database.tripDatabaseDao.insert(trip)
                    }
                }
            }
        }
    }
}