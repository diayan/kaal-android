package com.diayan.kaal.ui.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.repository.PlaceRepository
import com.diayan.kaal.di.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class PlacesViewModel @Inject constructor(
    private val repository: PlaceRepository,
    @CoroutineScopeIO private val coroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable: Boolean = false
    var placeId: Int? = null


    val places by lazy {
        repository.observePagedPlaces(connectivityAvailable, placeId, coroutineScope)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}