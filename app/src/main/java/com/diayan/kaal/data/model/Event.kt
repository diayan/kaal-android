package com.diayan.kaal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event (

    @PrimaryKey
    val id: Int,
    val name: String,
    val rates: Int,
    val description: String,
    val location: String,
    //val imageUrl: List<String>,
    val link: String,
    val type: String,
    val favorite: Boolean
)