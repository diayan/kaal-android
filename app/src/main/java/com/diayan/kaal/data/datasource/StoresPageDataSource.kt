package com.diayan.kaal.data.datasource

import androidx.paging.PageKeyedDataSource
import com.diayan.kaal.data.Result
import com.diayan.kaal.data.dao.StoreDao
import com.diayan.kaal.data.model.Store
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class StoresPageDataSource@Inject constructor(
    private val storeId: Int? = null,
    private val dataSource: StoresRemoteDataSource,
    private val dao: StoreDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Store>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Store>) {
        fetchData(1, params.requestedLoadSize){
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Store>) {
        val page = params.key
        fetchData(1, params.requestedLoadSize){
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Store>) {
        val page = params.key
        fetchData(1, params.requestedLoadSize){
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<Store>) -> Unit){
        scope.launch(getJobErrorHandler()){
            val response = dataSource.fetchStores(page, pageSize, storeId)
            if (response.status == Result.Status.SUCCESS){
                val results = response.data!!.results
                dao.insertAll(results)
                callback(results)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, throwable ->
        postError(throwable.message?: throwable.toString())
    }

    private fun postError(message: String){
        Timber.e("An error happened $message")
    }
}