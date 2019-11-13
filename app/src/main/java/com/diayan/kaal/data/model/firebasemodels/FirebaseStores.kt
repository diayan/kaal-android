package com.diayan.kaal.data.model.firebasemodels

class FirebaseStores (
    val id: Int = 0,
    val name: String = "",
    val description: String ="",
    val ratings: Int = 0,
    val comments: String ="",
    val location: String = "",
    //val imageUrl: List<String>,
    val externalLink: String = "",
    val type: String = "",
    val favorites: Boolean = true
)