package com.diayan.kaal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.data.model.firebasemodels.FirebaseEvents
import com.diayan.kaal.data.repository.EventRepository
import com.diayan.kaal.di.CoroutineScopeIO
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val repository: EventRepository,
    @CoroutineScopeIO
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _eventsLiveData = MutableLiveData<List<FirebaseEvents>>()
    val eventsLiveData: LiveData<List<FirebaseEvents>> get() = _eventsLiveData


    var connectivityAvailable: Boolean = false
    var eventId: Int? = null

    fun getEvents() {
        viewModelScope.launch {
            repository.getEvents().let {
                _eventsLiveData.postValue(it.map { event -> event.toObject<FirebaseEvents>() } as List<FirebaseEvents>?)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()

    }
}