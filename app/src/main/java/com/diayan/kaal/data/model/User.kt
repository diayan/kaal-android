package com.diayan.kaal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (

    @PrimaryKey
    val id: Int,
    val uid: String,
    val name: String
)