package com.diayan.kaal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.repository.EventRepository
import com.diayan.kaal.di.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val repository: EventRepository,
    @CoroutineScopeIO private val  coroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable: Boolean = false
    var eventId: Int? = null

    val events by lazy {
        repository.observePagedEvents(connectivityAvailable, eventId, coroutineScope)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()

    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}