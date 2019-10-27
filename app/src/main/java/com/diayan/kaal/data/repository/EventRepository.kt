package com.diayan.kaal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.diayan.kaal.data.dao.EventDao
import com.diayan.kaal.data.datasource.EventsPageDataSourceFactory
import com.diayan.kaal.data.datasource.EventsRemoteDataSource
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.data.resultLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val dao: EventDao,
    private val eventsRemoteDataSource: EventsRemoteDataSource) {

    fun observePagedEvents(
        connectivityAvailable: Boolean, eventId: Int? = null,
        coroutineScope: CoroutineScope
    ) = if (connectivityAvailable) observeRemotePagedEvents(eventId, coroutineScope)
    else observeLocalPagedEvents(eventId)

    private fun observeLocalPagedEvents(eventId: Int? = null): LiveData<PagedList<Event>> {
        val dataSourceFactory =
            if (eventId == null) dao.getPagedEvents()
            else dao.getPagedEventsById(eventId)

        return LivePagedListBuilder(
            dataSourceFactory,
            EventsPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeRemotePagedEvents(
        eventId: Int? = null,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<Event>> {
        val dataSourceFactory = EventsPageDataSourceFactory(
            eventId, eventsRemoteDataSource,
            dao, coroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            EventsPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeEvent(id: String) = resultLiveData(
        databaseQuery = { dao.getEvents(id) },
        networkCall = { eventsRemoteDataSource.fetchEvent(id) },
        saveCallResult = { dao.insert(it) })
        .distinctUntilChanged()

    private fun observeRemotePagedSets(eventId: Int? = null, coroutineScope: CoroutineScope)
            : LiveData<PagedList<Event>> {
        val dataSourceFactory = EventsPageDataSourceFactory(
            eventId, eventsRemoteDataSource,
            dao, coroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            EventsPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    companion object {

        const val PAGE_SIZE = 100

        // For Singleton instantiation
        @Volatile
        private var instance: EventRepository? = null

        fun getInstance(dao: EventDao, legoSetRemoteDataSource: EventsRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: EventRepository(dao, legoSetRemoteDataSource).also { instance = it }
            }
    }

    /*   fun observeSetsByTheme(id: Int) = resultLiveData(
           databaseQuery = { dao.getEvents(id) },
           networkCall = { eventsRemoteDataSource.fetchEvents(1, PAGE_SIZE, id) },
           saveCallResult = { dao.insertAll(it.results) })*/

}