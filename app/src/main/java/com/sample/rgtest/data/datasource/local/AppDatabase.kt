package com.sample.rgtest.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sample.rgtest.data.entity.FeedItem

/**
 * @author Mukesh
 * Application database class
 */

@Database(entities = [FeedItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun feedItemDao(): FeedItemDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        /**
         * get the Data base object with following the singleton pattern
         */
        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        /**
         * Data base building
         */
        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "feeddata")
                .fallbackToDestructiveMigration()
                .build()
    }

}