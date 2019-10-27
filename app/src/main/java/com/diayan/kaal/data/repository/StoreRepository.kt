package com.diayan.kaal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.diayan.kaal.data.dao.StoreDao
import com.diayan.kaal.data.datasource.StoresPageDataSourceFactory
import com.diayan.kaal.data.datasource.StoresRemoteDataSource
import com.diayan.kaal.data.model.Store
import com.diayan.kaal.data.resultLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(
    private val dao: StoreDao,
    private val storesRemoteDataSource: StoresRemoteDataSource) {

    fun observePagedStores(
        connectivityAvailable: Boolean, storeId: Int? = null,
        coroutineScope: CoroutineScope
    ) = if (connectivityAvailable) observeRemotePagedStores(storeId, coroutineScope)
    else observeLocalPagedStores(storeId)

    private fun observeLocalPagedStores(storeId: Int? = null): LiveData<PagedList<Store>> {
        val dataSourceFactory =
            if (storeId == null) dao.getPagedStores()
            else dao.getPagedStoresById(storeId)

        return LivePagedListBuilder(
            dataSourceFactory,
            StoresPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeRemotePagedStores(
        storeId: Int? = null,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<Store>> {
        val dataSourceFactory = StoresPageDataSourceFactory(
            storeId, storesRemoteDataSource,
            dao, coroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            StoresPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeEvent(id: String) = resultLiveData(
        databaseQuery = { dao.getStores(id) },
        networkCall = { storesRemoteDataSource.fetchStore(id) },
        saveCallResult = { dao.insert(it) })
        .distinctUntilChanged()

    private fun observeRemotePagedSets(storeId: Int? = null, coroutineScope: CoroutineScope)
            : LiveData<PagedList<Store>> {
        val dataSourceFactory = StoresPageDataSourceFactory(
            storeId, storesRemoteDataSource,
            dao, coroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            StoresPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    companion object {

        const val PAGE_SIZE = 100

        // For Singleton instantiation
        @Volatile
        private var instance: StoreRepository? = null

        fun getInstance(dao: StoreDao, storesRemoteDataSource: StoresRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: StoreRepository(dao, storesRemoteDataSource).also { instance = it }
            }
    }
}