package com.diayan.kaal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diayan.kaal.app.AppConfig.DATABASE_NAME
import com.diayan.kaal.data.dao.EventDao
import com.diayan.kaal.data.dao.PlaceDao
import com.diayan.kaal.data.dao.StoreDao
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.data.model.Place
import com.diayan.kaal.data.model.Store
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

/**
 * The Room database for this app
 */
@Database(
    entities = [Place::class, Event::class, Store::class],
    version = 1, exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placesDao(): PlaceDao

    abstract fun eventsDao(): EventDao

    abstract fun storesDao(): StoreDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}