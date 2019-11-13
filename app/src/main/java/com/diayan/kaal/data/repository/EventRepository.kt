package com.diayan.kaal.data.repository

import com.diayan.kaal.data.dao.EventDao
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
         val dao: EventDao,
         private val firebaseFirestore: FirebaseFirestore) {

    fun getEvents(){

    }

    fun getEventByType(){

    }

    fun getEventById(){

    }
}