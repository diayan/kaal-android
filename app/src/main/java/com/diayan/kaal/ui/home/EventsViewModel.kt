package com.diayan.kaal.ui.home

import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.FirebaseQueryLiveData
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.data.model.EventsEntity
import com.diayan.kaal.data.repository.EventRepository
import com.diayan.kaal.di.CoroutineScopeIO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val repository: EventRepository,
    @CoroutineScopeIO
    private val  coroutineScope: CoroutineScope
) : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.getReference("events")
    private val liveData = FirebaseQueryLiveData(databaseReference)
    private val placesLiveData: LiveData<EventsEntity> = Transformations.map(liveData, Deserializer())

    private inner class Deserializer: Function<DataSnapshot, EventsEntity> {
        override fun apply(dataSnapshot: DataSnapshot?): EventsEntity {
            return dataSnapshot?.getValue(EventsEntity::class.java)!!
        }
    }

    fun getEventsLiveData(): LiveData<EventsEntity> {
        return placesLiveData
    }

    var connectivityAvailable: Boolean = false
    var eventId: Int? = null

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()

    }
}