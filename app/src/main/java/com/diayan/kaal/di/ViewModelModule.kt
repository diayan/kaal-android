package com.diayan.kaal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diayan.kaal.ui.favorites.FavoriteViewModel
import com.diayan.kaal.ui.authentication.AuthViewModel
import com.diayan.kaal.ui.home.RegionsViewModel
import com.diayan.kaal.ui.schedules.SchedulesViewModel
import com.diayan.kaal.ui.stores.StoresViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegionsViewModel::class)
    abstract fun bindHomeViewModel(viewModel: RegionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SchedulesViewModel::class)
    abstract fun bindPlacesViewModel(viewModel: SchedulesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StoresViewModel::class)
    abstract fun bindStoresViewModel(viewModel: StoresViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindArViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindsAuthPreviewViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}