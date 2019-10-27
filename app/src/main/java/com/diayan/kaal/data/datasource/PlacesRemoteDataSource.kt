package com.diayan.kaal.data.datasource

import com.diayan.kaal.api.ApiService
import com.diayan.kaal.api.BaseDataSource

class PlacesRemoteDataSource(private val service: ApiService): BaseDataSource() {
    suspend fun fetchPlaces(page: Int, pageSize: Int? = null, placeId: Int? = null)
    = getResult{(service.getPlaces(page, pageSize, placeId))}

    suspend fun fetchPlace(id: String)
    =getResult{(service.getPlace(id))}
}