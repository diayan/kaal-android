package com.diayan.kaal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diayan.kaal.data.model.firebasemodels.FirebaseRegions
import com.diayan.kaal.data.repository.RegionsRepository
import com.diayan.kaal.di.CoroutineScopeIO
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegionsViewModel @Inject constructor(
    private val repository: RegionsRepository,
    @CoroutineScopeIO
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _eventsLiveData = MutableLiveData<List<FirebaseRegions>>()
    val regionsLiveData: LiveData<List<FirebaseRegions>> get() = _eventsLiveData

//    var connectivityAvailable: Boolean = false
//    var eventId: Int? = null

    fun getEvents() {
        viewModelScope.launch {
            repository.getEvents().let {
                _eventsLiveData.postValue(it.map { event -> event.toObject<FirebaseRegions>() } as List<FirebaseRegions>?)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()

    }
}