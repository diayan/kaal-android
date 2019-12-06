package com.diayan.kaal.data.model.firebasemodels

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseEvents(
        val id: String = "z",
        val name: String = "",
        val rates: Int = 0,
        val description: String ="",
        val location: String ="",
        val link: String ="",
        val type: String ="",
        val favorite: Boolean = false,
        val imageUrl:String = ""

        )