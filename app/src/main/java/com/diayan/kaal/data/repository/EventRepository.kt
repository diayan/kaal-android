package com.diayan.kaal.data.repository

import com.diayan.kaal.data.dao.EventDao
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
         val dao: EventDao) {

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("events")

    fun getEvents(){


    }
}