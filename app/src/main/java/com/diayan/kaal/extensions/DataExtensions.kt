package com.diayan.kaal.extensions

import com.diayan.kaal.data.model.Regions
import com.diayan.kaal.data.model.Schedules
import com.diayan.kaal.data.model.Store
import com.diayan.kaal.data.model.firebasemodels.FirebaseRegions
import com.diayan.kaal.data.model.firebasemodels.FirebaseSchedules
import com.diayan.kaal.data.model.firebasemodels.FirebaseStores
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

internal val FirebaseSchedules.toPlaces: Schedules
    get() = Schedules(
        this.id ?: 0,
        this.name ?: "",
        this.ratings ?: 0,
        this.description ?: "",
        this.imageUrl ?: "",
        this.imageUrl ?: "",
        this.externalLinkUrl ?: ""
    )

internal val FirebaseRegions.toEvents: Regions
    get() = Regions(
        this.id ?: 1,
        this.name ?: "",
        this.rates ?: 0,
        this.description ?: "",
        this.location ?: "",
        this.link ?: "",
        this.type ?: "",
        this.favorite ?: true,
        this.imageUrl ?: imageUrl
    )

internal val FirebaseStores.toStores: Store
    get() = Store(
        this.id ?: 0,
        this.name ?: "",
        this.description ?: "",
        this.ratings ?: 0,
        this.comments ?: "",
        this.location ?: "",
        this.externalLink ?: "",
        this.type ?: "",
        this.favorites ?: true
    )