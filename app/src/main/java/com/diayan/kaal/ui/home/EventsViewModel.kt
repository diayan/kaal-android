package com.diayan.kaal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.repository.EventRepository
import com.diayan.kaal.di.CoroutineScopeIO
import dagger.internal.DoubleCheck.lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val repository: EventRepository,
    @CoroutineScopeIO private val  coroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable: Boolean = false
    var eventId: Int? = null

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()

    }
}