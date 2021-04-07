package com.diayan.kaal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Schedules(

    @PrimaryKey
    val id: Int,
    val destination: String,
    //val date:Timestamp,
    val image: String?,
    val region: String?
)