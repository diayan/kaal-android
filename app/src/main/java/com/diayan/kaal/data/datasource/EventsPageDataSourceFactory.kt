package com.diayan.kaal.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.diayan.kaal.data.dao.EventDao
import com.diayan.kaal.data.model.Event
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class EventsPageDataSourceFactory @Inject constructor(
    private val eventId: Int? = null,
    private val dataSource: EventsRemoteDataSource,
    private val dao: EventDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Event>() {

    private val liveData = MutableLiveData<EventsPageDataSource>()

    override fun create(): DataSource<Int, Event> {
        val source = EventsPageDataSource(eventId, dataSource, dao, scope)
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