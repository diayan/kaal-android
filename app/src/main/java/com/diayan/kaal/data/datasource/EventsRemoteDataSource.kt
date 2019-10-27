package com.diayan.kaal.data.datasource

import com.diayan.kaal.api.ApiService
import com.diayan.kaal.api.BaseDataSource

class EventsRemoteDataSource(private val service: ApiService): BaseDataSource() {

    suspend fun fetchEvents(page: Int, pageSize: Int? = null, eventId: Int? = null)
    = getResult{(service.getEvents(page, pageSize, "-year"))}

    suspend fun fetchEvent(id: String) =
        getResult{service.getEvent(id)}
}