package com.diayan.kaal.di

import com.diayan.kaal.ui.ar.ArFragment
import com.diayan.kaal.ui.authentication.AuthPreviewFragment
import com.diayan.kaal.ui.authentication.RegistrationFragment
import com.diayan.kaal.ui.authentication.SignInFragment
import com.diayan.kaal.ui.home.RegionsFragment
import com.diayan.kaal.ui.schedules.SchedulesFragment
import com.diayan.kaal.ui.stores.StoresFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): RegionsFragment

    @ContributesAndroidInjector
    abstract fun contributePlacesFragment(): SchedulesFragment

    @ContributesAndroidInjector
    abstract fun contributeStoresFragment(): StoresFragment

    @ContributesAndroidInjector
    abstract fun contributeArFragment(): ArFragment

    @ContributesAndroidInjector
    abstract fun contributeAuthPreviewFragment(): AuthPreviewFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun contributeSignInFragment(): SignInFragment

}