package com.diayan.kaal.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.diayan.kaal.data.Result
import com.diayan.kaal.data.dao.EventDao
import com.diayan.kaal.data.model.Event
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class EventsPageDataSource@Inject constructor(
    private val eventId: Int? = null,
    private val dataSource: EventsRemoteDataSource,
    private val dao: EventDao,
    private val scope: CoroutineScope) : PageKeyedDataSource<Int, Event>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Event>) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<Event>) -> Unit) {
        scope.launch(getJobErrorJobHandler()) {
            val response = dataSource.fetchEvents(page, pageSize, eventId)
            Log.d("events response ", Gson().toJson(response) )
            if (response.status == Result.Status.SUCCESS) {
                val result = response.data!!.results
                dao.insertAll(result)
                callback(result)
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }

    }

    private fun getJobErrorJobHandler() = CoroutineExceptionHandler { _, throwable ->
        postError(throwable.message ?: throwable.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error occurred: $message")
    }
}