package com.diayan.kaal.ui.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.model.Schedules
import com.diayan.kaal.data.repository.SchedulesRepository
import com.diayan.kaal.di.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class SchedulesViewModel @Inject constructor(
    private val repository: SchedulesRepository,
    @CoroutineScopeIO private val coroutineScope: CoroutineScope
) : ViewModel() {

    var placeId: Int? = null
    var connectivityAvailable: Boolean = false

    private val _placesLiveData = MutableLiveData<Schedules>()
    val placesLiveData: LiveData<Schedules> get() = _placesLiveData


    fun getAllPlaces() {

    }

    fun getMostLovedPlaces() {

    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

}