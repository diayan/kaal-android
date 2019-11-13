package com.diayan.kaal.data.repository

import com.diayan.kaal.common.Result
import com.diayan.kaal.data.dao.PlaceDao
import com.diayan.kaal.data.model.Place
import com.diayan.kaal.data.model.firebasemodels.FirebasePlaces
import com.diayan.kaal.extensions.awaitTaskResult
import com.diayan.kaal.extensions.toPlaces
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val dao: PlaceDao,
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getAllPlaces(): Result<Exception, List<Place>> {
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

    private fun resultToPlacesList(result: QuerySnapshot?): Result<Exception, List<Place>> {
        val placeList = mutableListOf<Place>()

        result?.forEach { documentSnapshot ->
            placeList.add(documentSnapshot.toObject(FirebasePlaces::class.java).toPlaces)
        }

        return Result.build {
            placeList
        }
    }
}