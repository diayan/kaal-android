package com.diayan.kaal.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.diayan.kaal.data.dao.StoreDao
import com.diayan.kaal.data.model.Store
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class StoresPageDataSourceFactory @Inject constructor(
    private val storeId: Int? = null,
    private val dataSource: StoresRemoteDataSource,
    private val dao: StoreDao,
    private val scope: CoroutineScope): DataSource.Factory<Int, Store>() {

    private val liveData =  MutableLiveData<StoresPageDataSource>()

    override fun create(): DataSource<Int, Store> {
        val source = StoresPageDataSource(storeId, dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object{
        private const val PAGE_SIZE = 100

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

    }
}