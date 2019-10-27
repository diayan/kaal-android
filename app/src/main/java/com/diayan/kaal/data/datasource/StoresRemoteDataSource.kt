package com.diayan.kaal.data.datasource

import com.diayan.kaal.api.ApiService
import com.diayan.kaal.api.BaseDataSource

class StoresRemoteDataSource(private val service: ApiService):BaseDataSource() {

    suspend fun fetchStores(page: Int, pageSize: Int? = null, storeId: Int? = null)
    = getResult{(service.getStores(page, pageSize, "-year"))}

    suspend fun fetchStore(id: String) =
        getResult{(service.getStore(id))}

}