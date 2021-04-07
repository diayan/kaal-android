package com.diayan.kaal.data.repository

import com.diayan.kaal.data.dao.RegionsDao
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionsRepository @Inject constructor(
    val dao: RegionsDao,
    private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun getEvents(): MutableList<DocumentSnapshot> = firebaseFirestore.collection("regions").get().await().documents

/*    suspend fun getAllEvents(): Result<Exception, List<Regions>> {
        return try {
            val task = awaitTaskResult(
                firebaseFirestore.collection("regions")
                    .get()
            )
            resultToPlacesList(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }
    private fun resultToPlacesList(result: QuerySnapshot?): Result<Exception, List<Regions>> {
        val eventList = mutableListOf<Regions>()

        result?.forEach { documentSnapshot ->
            eventList.add(documentSnapshot.toObject(FirebaseRegion::class.java).toEvents)
        }

        return Result.build {
            eventList
        }
    }*/
}