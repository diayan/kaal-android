package com.diayan.kaal.data.repository

import com.diayan.kaal.data.dao.EventDao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    val dao: EventDao,
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getEvents() = firebaseFirestore.collection("regions").get().await().documents


//    suspend fun getAllEvents(): Result<Exception, List<Event>> {
//        return try {
//            val task = awaitTaskResult(
//                firebaseFirestore.collection("regions")
//                    .get()
//            )
//            resultToPlacesList(task)
//        } catch (exception: Exception) {
//            Result.build { throw exception }
//        }
//    }
//    private fun resultToPlacesList(result: QuerySnapshot?): Result<Exception, List<Event>> {
//        val eventList = mutableListOf<Event>()
//
//        result?.forEach { documentSnapshot ->
//            eventList.add(documentSnapshot.toObject(FirebaseEvents::class.java).toEvents)
//        }
//
//        return Result.build {
//            eventList
//        }
//    }
}