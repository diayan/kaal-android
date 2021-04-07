package com.diayan.kaal.ui.schedules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diayan.kaal.data.model.firebasemodels.FirebaseSchedule
import com.diayan.kaal.data.repository.SchedulesRepository
import com.diayan.kaal.di.CoroutineScopeIO
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SchedulesViewModel @Inject constructor(
    private val repository: SchedulesRepository,
    @CoroutineScopeIO private val coroutineScope: CoroutineScope
) : ViewModel() {

    var tripId: Int? = null
    var connectivityAvailable: Boolean = false
    private val _scheduledTripsLiveData = MutableLiveData<List<FirebaseSchedule>>()
    val scheduledTripsLiveData: LiveData<List<FirebaseSchedule>> get() = _scheduledTripsLiveData


    fun getScheduledTrips() {
        viewModelScope.launch {
            repository.getScheduledTrips().let {
                _scheduledTripsLiveData.postValue(it.map { trip -> trip.toObject<FirebaseSchedule>() } as List<FirebaseSchedule>?)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}