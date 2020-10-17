package com.diayan.kaal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diayan.kaal.ui.ar.ArViewModel
import com.diayan.kaal.ui.authentication.AuthViewModel
import com.diayan.kaal.ui.home.EventsViewModel
import com.diayan.kaal.ui.places.PlacesViewModel
import com.diayan.kaal.ui.stores.StoresViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    abstract fun bindHomeViewModel(viewModel: EventsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    abstract fun bindPlacesViewModel(viewModel: PlacesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StoresViewModel::class)
    abstract fun bindStoresViewModel(viewModel: StoresViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArViewModel::class)
    abstract fun bindArViewModel(viewModel: ArViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindsAuthPreviewViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}