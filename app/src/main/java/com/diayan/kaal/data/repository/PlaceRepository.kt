package com.diayan.kaal.data.repository

import com.diayan.kaal.data.dao.PlaceDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
        private val dao: PlaceDao) {

}