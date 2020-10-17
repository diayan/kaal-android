package com.diayan.kaal.data.dao

import com.diayan.kaal.helper.FirebaseAuthHelper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OnBoardingDao @Inject() constructor(
    private val firestoreInstance: FirebaseFirestore,
    private val helper: FirebaseAuthHelper
) {

    fun updateUserPreference(data: MutableMap<String, Any>) {
        helper.currentUser()?.uid?.let {
            firestoreInstance.collection("users")
                .document(it)
                .set(data)
        }
    }

    suspend fun checkIfUserIsOnBoarded(uid: String) {
        firestoreInstance.collection("users")
            .document(uid)
            .get().await().getBoolean("isOnBoarded")
    }
}