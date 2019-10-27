package com.diayan.kaal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class Store(

    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val ratings: Int,
    val comments: String,
    val location: String,
    //val imageUrl: List<String>,
    val externalLink: String,
    val type: String,
    val favorites: Boolean

)