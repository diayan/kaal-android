package com.diayan.kaal.di

import com.diayan.kaal.ui.ar.ArFragment
import com.diayan.kaal.ui.home.EventsFragment
import com.diayan.kaal.ui.places.PlacesFragment
import com.diayan.kaal.ui.stores.StoresFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): EventsFragment

    @ContributesAndroidInjector
    abstract fun contributePlacesFragment(): PlacesFragment

    @ContributesAndroidInjector
    abstract fun contributeStoresFragment(): StoresFragment

    @ContributesAndroidInjector
    abstract fun contributeArFragment(): ArFragment

}