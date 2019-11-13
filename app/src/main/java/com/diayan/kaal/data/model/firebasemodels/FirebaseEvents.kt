package com.diayan.kaal.data.model.firebasemodels

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseEvents(
        val id: Int = 0,
        val name: String = "",
        val rates: Int = 0,
        val description: String ="",
        val location: String ="",
        //val imageUrl: List<String>,
        val link: String ="",
        val type: String ="",
        val favorite: Boolean = false
)