package com.diayan.kaal.ui.detail

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

class DetailViewModel @Inject constructor(
    private val repository: RegionsRepository,
    @CoroutineScopeIO
    private val coroutineScope: CoroutineScope
) : ViewModel()  {

    private val _regionsLiveData = MutableLiveData<List<FirebaseRegion>>()
    val regionLiveData: LiveData<List<FirebaseRegion>> get() = _regionsLiveData

    fun getRegions() {
        viewModelScope.launch {
            repository.getEvents().let {
                _regionsLiveData.postValue(it.map { event -> event.toObject<FirebaseRegion>() } as List<FirebaseRegion>?)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}