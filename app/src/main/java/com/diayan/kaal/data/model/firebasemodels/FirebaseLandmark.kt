package com.diayan.kaal.data.model.firebasemodels

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseLandmark(
        val id: Int = 1,
        val landmark: String = "",
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val community: String = "",
        val routes: Int = 12,
        val favorite: Boolean = false,
        val imageUrl: String = ""
)