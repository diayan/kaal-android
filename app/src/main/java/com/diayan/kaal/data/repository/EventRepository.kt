package com.diayan.kaal.data.repository

import com.diayan.kaal.common.Result
import com.diayan.kaal.data.dao.EventDao
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.data.model.firebasemodels.FirebaseEvents
import com.diayan.kaal.extensions.awaitTaskResult
import com.diayan.kaal.extensions.toEvents
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    val dao: EventDao,
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getAllEvents(): Result<Exception, List<Event>> {
        return try {
            val task = awaitTaskResult(
                firebaseFirestore.collection("events")
                    .get()
            )
            resultToPlacesList(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    suspend fun getEvents() = firebaseFirestore.collection("events").get().await().documents

    fun getEventByType() {}

    fun getEventById() {}

    private fun resultToPlacesList(result: QuerySnapshot?): Result<Exception, List<Event>> {
        val eventList = mutableListOf<Event>()

        result?.forEach { documentSnapshot ->
            eventList.add(documentSnapshot.toObject(FirebaseEvents::class.java).toEvents)
        }

        return Result.build {
            eventList
        }
    }
}