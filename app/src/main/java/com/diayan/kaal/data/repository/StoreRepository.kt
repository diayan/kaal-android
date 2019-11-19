package com.diayan.kaal.data.repository

import com.diayan.kaal.common.Result
import com.diayan.kaal.data.dao.StoreDao
import com.diayan.kaal.data.model.Store
import com.diayan.kaal.data.model.firebasemodels.FirebaseStores
import com.diayan.kaal.extensions.awaitTaskResult
import com.diayan.kaal.extensions.toStores
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(
    private val dao: StoreDao,
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getAllStores(): Result<Exception, List<Store>> {
        return try {
            val task = awaitTaskResult(
                firebaseFirestore.collection("places")
                    .get()
            )
            resultToStoreList(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    fun getStoreByType() {}

    fun getStoreById() {}

    private fun resultToStoreList(result: QuerySnapshot?): Result<Exception, List<Store>> {
        val storeList = mutableListOf<Store>()
        result?.forEach { documentSnapshot ->
            storeList.add(documentSnapshot.toObject(FirebaseStores::class.java).toStores)
        }

        return Result.build {
            storeList
        }
    }
}