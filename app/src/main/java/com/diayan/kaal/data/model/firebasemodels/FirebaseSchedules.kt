package com.diayan.kaal.data.model.firebasemodels

class FirebaseSchedules (
    val id: Int = 0,
    val name: String = "",
    val ratings: Int? = 0 ,
    val description:String = "",
    val imageUrl: String? = "",
    val videoUrl: String? = "",
    //val rates: List<String?>,
    val externalLinkUrl: String? = ""
)