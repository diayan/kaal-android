package com.diayan.kaal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.diayan.kaal.data.dao.PlaceDao
import com.diayan.kaal.data.datasource.PlacesPageDataSourceFactory
import com.diayan.kaal.data.datasource.PlacesRemoteDataSource
import com.diayan.kaal.data.model.Place
import com.diayan.kaal.data.resultLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val dao: PlaceDao,
    private val placesRemoteDataSource: PlacesRemoteDataSource
) {

    fun observePagedPlaces(
        connectivityAvailable: Boolean, placeId: Int? = null,
        coroutineScope: CoroutineScope
    ) = if (connectivityAvailable) observeRemotePagedPlaces(placeId, coroutineScope)
    else observeLocalPagedPlaces(placeId)

    private fun observeLocalPagedPlaces(placeId: Int? = null): LiveData<PagedList<Place>> {
        val dataSourceFactory =
            if (placeId == null) dao.getPagedPlaces()
            else dao.getPagedPlacesById(placeId)

        return LivePagedListBuilder(
            dataSourceFactory,
            PlacesPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeRemotePagedPlaces(
        placeId: Int? = null,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<Place>> {
        val dataSourceFactory = PlacesPageDataSourceFactory(
            placeId, placesRemoteDataSource,
            dao, coroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            PlacesPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observePlace(id: String) = resultLiveData(
        databaseQuery = { dao.getPlaces(id) },
        networkCall = { placesRemoteDataSource.fetchPlace(id) },
        saveCallResult = { dao.insert(it) })
        .distinctUntilChanged()

    private fun observeRemotePagedSets(placeId: Int? = null, coroutineScope: CoroutineScope)
            : LiveData<PagedList<Place>> {
        val dataSourceFactory = PlacesPageDataSourceFactory(
            placeId, placesRemoteDataSource,
            dao, coroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            PlacesPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    companion object {

        const val PAGE_SIZE = 100

        // For Singleton instantiation
        @Volatile
        private var instance: PlaceRepository? = null

        fun getInstance(dao: PlaceDao, legoSetRemoteDataSource: PlacesRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: PlaceRepository(dao, legoSetRemoteDataSource).also { instance = it }
            }
    }

}