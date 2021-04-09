package com.diayan.kaal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diayan.kaal.data.model.firebasemodels.FirebaseRegion
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

    private val _eventsLiveData = MutableLiveData<List<FirebaseRegion>>()
    val regionLiveData: LiveData<List<FirebaseRegion>> get() = _eventsLiveData

    fun getEvents() {
        viewModelScope.launch {
            repository.getEvents().let {
                _eventsLiveData.postValue(it.map { event -> event.toObject<FirebaseRegion>() } as List<FirebaseRegion>?)
            }
        }
    }

    fun getLandmarks() {
        viewModelScope.launch {
            repository.getLandmarks().let {
                //_eventsLiveData.postValue()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()

    }
}