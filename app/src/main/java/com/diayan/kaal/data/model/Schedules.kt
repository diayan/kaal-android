package com.diayan.kaal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Place(

    @PrimaryKey
    val id: Int,
    val name: String,
    val ratings: Int?,
    val description:String,
    val imageUrl: String?,
    val videoUrl: String?,
    //val rates: List<String?>,
    val externalLinkUrl: String?
)