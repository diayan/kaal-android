package com.diayan.kaal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.diayan.kaal.data.dao.EventDao
import com.diayan.kaal.data.dao.PlaceDao
import com.diayan.kaal.data.dao.StoreDao
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.data.model.Place
import com.diayan.kaal.data.model.Store
import com.diayan.kaal.worker.SeedDatabaseWorker

/**
 * The Room database for this app
 */
@Database(
    entities = [Place::class,
        Event::class, Store::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placesDao(): PlaceDao

    abstract fun eventsDao(): EventDao

    abstract fun StoresDao(): StoreDao


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "legocatalog-db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                       // val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        //WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}