package com.diayan.kaal.ui.stores

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.repository.StoreRepository
import com.diayan.kaal.di.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class StoresViewModel @Inject constructor(
    private val repository: StoreRepository,
    @CoroutineScopeIO private val coroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable = false
    var storeId: Int? = null

    val stores by lazy {
        repository.observePagedStores(connectivityAvailable, storeId, coroutineScope)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}