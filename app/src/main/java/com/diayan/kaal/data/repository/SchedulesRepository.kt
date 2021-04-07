package com.diayan.kaal.data.repository

import com.diayan.kaal.common.Result
import com.diayan.kaal.data.dao.SchedulesDao
import com.diayan.kaal.data.model.Schedules
import com.diayan.kaal.data.model.firebasemodels.FirebaseSchedule
import com.diayan.kaal.extensions.awaitTaskResult
import com.diayan.kaal.extensions.toPlaces
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SchedulesRepository @Inject constructor(
    private val dao: SchedulesDao,
    private val firebaseFireStore: FirebaseFirestore
) {

    suspend fun getScheduledTrips(): MutableList<DocumentSnapshot> = firebaseFireStore.collection("trips").get().await().documents


    suspend fun getAllPlaces(): Result<Exception, List<Schedules>> {
        return try {
            val task = awaitTaskResult(
                firebaseFireStore.collection("places")
                    .get()
            )
            resultToPlacesList(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    private fun resultToPlacesList(result: QuerySnapshot?): Result<Exception, List<Schedules>> {
        val placeList = mutableListOf<Schedules>()

        result?.forEach { documentSnapshot ->
            placeList.add(documentSnapshot.toObject(FirebaseSchedule::class.java).toPlaces)
        }

        return Result.build {
            placeList
        }
    }
}