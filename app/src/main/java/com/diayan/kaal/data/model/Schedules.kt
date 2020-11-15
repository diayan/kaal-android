package com.diayan.kaal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

@Entity(tableName = "places")
data class Schedules(

    @PrimaryKey
    val id: Int,
    val destination: String,
    //val date:Timestamp,
    val image: String?,
    val region: String?
)