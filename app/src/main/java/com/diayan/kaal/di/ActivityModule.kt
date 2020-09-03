package com.diayan.kaal.di

import com.diayan.kaal.ui.detail.DetailActivity
import com.diayan.kaal.MainActivity
import com.diayan.kaal.ui.authentication.AuthenticationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeAuthenticationActivity(): AuthenticationActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity
}