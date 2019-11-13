package com.diayan.kaal.ui.stores

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.model.Store
import com.diayan.kaal.data.repository.StoreRepository
import com.diayan.kaal.di.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoresViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    @CoroutineScopeIO private val coroutineScope: CoroutineScope
) : ViewModel() {

    var storeId: Int? = null
    var connectivityAvailable = false
    private val _storesLiveData = MutableLiveData<Store>()
    val storesLiveData: LiveData<Store> get() = _storesLiveData

    private fun getEvents() = coroutineScope.launch {
        val eventResult = storeRepository.getAllStores()
    }

    private fun getEventsById() = coroutineScope.launch {
        val eventResult = storeRepository.getStoreById()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}