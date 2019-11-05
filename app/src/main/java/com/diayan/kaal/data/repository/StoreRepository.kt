package com.diayan.kaal.data.repository

import com.diayan.kaal.data.dao.StoreDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(
        private val dao: StoreDao) {

}