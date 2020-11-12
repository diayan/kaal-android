package com.diayan.kaal.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.diayan.kaal.data.AppDatabase
import com.diayan.kaal.data.model.Schedules
import com.diayan.kaal.util.DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @InternalCoroutinesApi
    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {

            try {
                applicationContext.assets.open(DATA_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val type = object : TypeToken<List<Schedules>>() {}.type
                        val list: List<Schedules> = Gson().fromJson(jsonReader, type)

                        AppDatabase.getInstance(applicationContext).placesDao().insertAll(list)

                        Result.success()
                    }
                }
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}