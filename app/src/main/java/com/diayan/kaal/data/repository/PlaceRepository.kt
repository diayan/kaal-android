package com.diayan.kaal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.diayan.kaal.data.dao.PlaceDao
import com.diayan.kaal.data.model.Place
import com.diayan.kaal.data.resultLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
        private val dao: PlaceDao) {

}