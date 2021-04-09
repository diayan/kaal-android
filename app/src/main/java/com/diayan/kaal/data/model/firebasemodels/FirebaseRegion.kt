package com.diayan.kaal.data.model.firebasemodels

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseRegion(
        val id: Int = 1,
        val name: String ="",
        val rates: Int = 0,
        val description: String ="",
        val location: String ="",
        val link: String ="",
        val type: String ="",
        val favorite: Boolean = false,
        val imageUrl:String = "",
        val landmark: List<FirebaseLandmark> = listOf()
)