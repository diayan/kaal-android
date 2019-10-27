package com.diayan.kaal.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.diayan.kaal.data.Result
import com.diayan.kaal.data.dao.PlaceDao
import com.diayan.kaal.data.model.Place
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class PlacesPageDataSource @Inject constructor(
    private val placeId: Int? = null,
    private val dataSource: PlacesRemoteDataSource,
    private val dao: PlaceDao,
    private val scope: CoroutineScope
): PageKeyedDataSource<Int, Place>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Place>) {
        fetchData(1, params.requestedLoadSize){
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Place>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize){
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Place>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize){
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback:( List<Place>) -> Unit){
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchPlaces(page, pageSize, placeId)
            Log.d("events response ", Gson().toJson(response) )

            if (response.status == Result.Status.SUCCESS){
                val results = response.data!!.results
                dao.insertAll(results)
                callback(results)
            }else if (response.status == Result.Status.SUCCESS){
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, throwable ->
        postError(throwable.message?: throwable.toString())
    }

    private fun postError(message: String){
        Timber.e("An error happened: $message")
    }
}