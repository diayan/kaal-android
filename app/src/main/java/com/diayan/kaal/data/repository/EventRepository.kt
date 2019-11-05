package com.diayan.kaal.data.repository

import com.diayan.kaal.data.dao.EventDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
        private val dao: EventDao) {

}