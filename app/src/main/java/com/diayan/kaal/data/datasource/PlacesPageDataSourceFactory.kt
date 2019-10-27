package com.diayan.kaal.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.diayan.kaal.data.dao.PlaceDao
import com.diayan.kaal.data.model.Place
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class PlacesPageDataSourceFactory @Inject constructor(
    private val placeId: Int? = null,
    private val dataSource: PlacesRemoteDataSource,
    private val dao: PlaceDao,
    private val scope: CoroutineScope): DataSource.Factory<Int, Place>(){

    private val liveData = MutableLiveData<PlacesPageDataSource>()

    override fun create(): DataSource<Int, Place> {
        val source = PlacesPageDataSource(placeId, dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object{
        private val PAGE_SIZE = 100

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}