package com.diayan.kaal.extensions

import com.diayan.kaal.data.model.Place
import com.diayan.kaal.data.model.firebasemodels.FirebasePlaces
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal suspend fun <T> awaitTaskResult(task: Task<T>): T = suspendCoroutine { continuation ->
    task.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result!!)
        } else {
            continuation.resumeWithException(task.exception!!)
        }
    }
}

internal val FirebasePlaces.toPlaces: Place
    get() = Place(
        this.id ?: 0,
        this.name ?: "",
        this.ratings ?: 0,
        this.description ?: "",
        this.imageUrl?: "",
        this.imageUrl?: "",
        this.externalLinkUrl?: ""
    )