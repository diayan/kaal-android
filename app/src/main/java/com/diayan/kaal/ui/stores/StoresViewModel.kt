package com.diayan.kaal.ui.stores

import androidx.lifecycle.ViewModel
import com.diayan.kaal.data.repository.StoreRepository
import com.diayan.kaal.di.CoroutineScopeIO
import dagger.internal.DoubleCheck.lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class StoresViewModel @Inject constructor(
    private val repository: StoreRepository,
    @CoroutineScopeIO private val coroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable = false
    var storeId: Int? = null

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}