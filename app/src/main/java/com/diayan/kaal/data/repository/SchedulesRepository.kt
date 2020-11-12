package com.diayan.kaal.data.repository

import com.diayan.kaal.common.Result
import com.diayan.kaal.data.dao.SchedulesDao
import com.diayan.kaal.data.model.Schedules
import com.diayan.kaal.data.model.firebasemodels.FirebaseSchedules
import com.diayan.kaal.extensions.awaitTaskResult
import com.diayan.kaal.extensions.toPlaces
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SchedulesRepository @Inject constructor(
    private val dao: SchedulesDao,
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getAllPlaces(): Result<Exception, List<Schedules>> {
        return try {
            val task = awaitTaskResult(
                firebaseFirestore.collection("places")
                    .get()
            )
            resultToPlacesList(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    fun getPlaceByType() {}

    fun getPlaceById() {}

    private fun resultToPlacesList(result: QuerySnapshot?): Result<Exception, List<Schedules>> {
        val placeList = mutableListOf<Schedules>()

        result?.forEach { documentSnapshot ->
            placeList.add(documentSnapshot.toObject(FirebaseSchedules::class.java).toPlaces)
        }

        return Result.build {
            placeList
        }
    }
}