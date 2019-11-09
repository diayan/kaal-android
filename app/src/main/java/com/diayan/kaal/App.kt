package com.diayan.kaal

import android.app.Application
import com.diayan.kaal.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector {

    @set:Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}