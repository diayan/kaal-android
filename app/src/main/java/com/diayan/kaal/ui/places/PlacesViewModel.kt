package com.diayan.kaal.ui.places

import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.repository.PlaceRepository
import com.diayan.kaal.di.CoroutineScopeIO
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class PlacesViewModel @Inject constructor(
    private val repository: PlaceRepository,
    @CoroutineScopeIO private val coroutineScope: CoroutineScope
) : ViewModel() {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("places")


    var connectivityAvailable: Boolean = false
    var placeId: Int? = null

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

}